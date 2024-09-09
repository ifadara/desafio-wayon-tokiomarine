<template>
    <div >
        <BForm @submit.prevent="addTransfer">
          <BCard class="border">
            <button @click="addMode = false" class="close-btn">&times;</button>
            <BRow>
                <BCol md="3">
                    <BFormGroup
                    label="Conta de origem:*"
                    label-for="originAccount"
                    :state="!errors.originAccount ? true : false"
                    invalid-feedback="Conta de origem é obrigatória e deve ser um número válido."
                    max="10"
                    >
                    <BFormInput
                        id="originAccount"
                        v-model="addNew.originAccount"
                        placeholder="XXXXXXXXXX"
                        type="number"
                        :state="!errors.originAccount ? true : false"
                    />
                    </BFormGroup>
                </BCol>
                <BCol md="3">
                    <BFormGroup
                    label="Conta de destino:*"
                    label-for="targetAccount"
                    :state="!errors.targetAccount ? true : false"
                    invalid-feedback="Conta de destino é obrigatória e deve ser um número válido."
                    >
                    <BFormInput
                        id="targetAccount"
                        v-model="addNew.targetAccount"
                        placeholder="XXXXXXXXXX"
                        type="number"
                        :state="!errors.targetAccount ? true : false"
                        max="10"
                    />
                    </BFormGroup>
                </BCol>
                <BCol md="3">
                    <BFormGroup
                    label="Valor a ser transferido:*"
                    label-for="transferValue"
                    :state="!errors.transferValue ? true : false"
                    invalid-feedback="Valor é obrigatório e deve ser um valor numérico válido."
                    >
                    <BFormInput
                        id="transferValue"
                        v-model="addNew.transferValue"
                        :value="formattedValue"
                        @input="updateValue"
                        placeholder="R$ 0,00"
                        :state="!errors.transferValue ? true : false"
                    />
                    </BFormGroup>
                </BCol>
                <BCol md="3">
                    <BFormGroup
                    label="Qual a data de agendamento?:*"
                    label-for="scheduledDate"
                    :state="!errors.scheduledDate ? true : false"
                    invalid-feedback="Data de agendamento é obrigatória."
                    >
                    <BFormInput
                        id="scheduledDate"
                        v-model="addNew.scheduledDate"
                        type="date"
                        :state="!errors.scheduledDate ? true : false"
                    />
                    </BFormGroup>
                </BCol>
            </BRow>

            <BRow>
                <BCol class="text-right mt-2 p-2" md="12" align-self="end">
                    <BButton
                        variant="success"
                        class="btn-md"
                        type="submit"
                    >
                        <span>Salvar</span>
                    </BButton>
                    <BButton
                        class="btn-md"
                        variant="danger"
                        @click="resetForm"
                    >
                        Cancelar
                    </BButton>  
                </BCol>
            </BRow>
          </BCard>
        </BForm>
    </div>
    
      
      
    </template>
    
    <script>
    import configAxios from '@/configAxios';
    import {
        BCard,
        BRow,
        BCol,
        BForm,
        BFormInput,
        BFormGroup,
        BButton,
    } from 'bootstrap-vue-next'
    
    export default {
      components: {
        BCard,
        BRow,
        BCol,
        BForm,
        BFormInput,
        BFormGroup,
        BButton,
      },
      props: {
        dadosItens: { type: Object, default: () => {} },
      },

      computed: {
        formattedValue() {
            return this.formatValue(this.addNew.transferValue);
        },
        isFormValid() {
            return !this.errors.originAccount && 
                    !this.errors.targetAccount && 
                    !this.errors.transferValue && 
                    !this.errors.scheduledDate;
        }
      },

      data() {
        return{
            addNew: {
                originAccount: '',
                targetAccount: '',
                transferValue: '',
                scheduledDate: '',
            },
            errors: {},
        }
      },
      methods: {

        validateForm() {
            this.errors = {};
            if (this.addNew.originAccount.length !== 10) {
                this.errors.originAccount = true;
            }
            if (!this.addNew.targetAccount.length !== 10) {
                this.errors.targetAccount = true;
            }
            if (!this.addNew.transferValue || isNaN(this.removeSpecialCharacters(this.addNew.transferValue))) {
                this.errors.transferValue = true;
            }
            if (!this.addNew.scheduledDate) {
                this.errors.scheduledDate = true;
            }
        },

        formatValue(value) {
            if (!value) return 'R$ 0,00';
            let number = parseFloat(value.replace(/[^\d]/g, '')) / 100;
            return `R$ ${number.toFixed(2).replace('.', ',')}`;
        },

        updateValue(event) {
            const inputValue = event.target.value.replace(/[^\d]/g, '');
            this.addNew.transferValue = (inputValue)
        },
        removeSpecialCharacters(value) {
            // Remove o símbolo "R$" e espaços
            let newValue = value.replace(/[^0-9,]/g, '');
            
            // Substitui a vírgula por um ponto
            newValue = newValue.replace(',', '.');

            return newValue;
        },

        async addTransfer(){
            this.addNew.transferValue = this.addNew.transferValue.includes(',') ? this.removeSpecialCharacters(this.addNew.transferValue) : this.addNew.transferValue
            configAxios.post('/v1/transfer', this.addNew).then(() => {
                this.addNew = {}
                this.$emit('transfer-added')
            }).catch(() => {
                
            })
        },

        resetForm() {
            this.addNew = {}
            this.errors = {}
        },

      }
    }


    </script>
    
    <!-- Add "scoped" attribute to limit CSS to this component only -->
    <style scoped>
        .close-btn {
            position: absolute;
            top: 10px;
            right: 10px;
            background: none;
            border: none;
            font-size: 20px;
            cursor: pointer;
        }

        .close-btn:hover {
            color: red; 
        }
    </style>
    
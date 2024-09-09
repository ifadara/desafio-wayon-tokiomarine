<template>
    <div v-if="addMode">
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
                    >
                    <BFormInput
                        id="originAccount"
                        v-model="addNew.originAccount"
                        placeholder="XXXXXXXXXX"
                        type="number"
                        :state="!errors.originAccount ? true : false"
                        @input="validateForm"
                    />
                    </BFormGroup>
                </BCol>
                <BCol md="3">
                    <BFormGroup
                    label="Conta de destino:*"
                    label-for="targetAccount"
                    :state="!errors.targetAccount ? true : false"
                    @input="validateForm"
                    invalid-feedback="Conta de destino é obrigatória e deve ser um número válido."
                    >
                    <BFormInput
                        id="targetAccount"
                        v-model="addNew.targetAccount"
                        placeholder="XXXXXXXXXX"
                        type="number"
                        :state="!errors.targetAccount ? true : false"
                        @input="validateForm"
                    />
                    </BFormGroup>
                </BCol>
                <BCol md="3">
                    <BFormGroup
                    label="Valor a ser transferido:*"
                    label-for="transferValue"
                    :state="!errors.transferValue ? true : false"
                    @input="validateForm"
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
                        @input="validateForm"
                        :state="!errors.scheduledDate ? true : false"
                    />
                    </BFormGroup>
                </BCol>
            </BRow>

            <div v-if="submissionError" class="alert alert-danger mt-2">
                <button @click="submissionError = null" class="close-btn">&times;</button>
                {{ submissionError }}
            </div>

            <BRow>
                <BCol class="text-right mt-2 p-2" md="12" align="right">
                    <BButton
                        variant="success"
                        class="btn-md mr-3"
                        type="submit"
                        :disabled="!isFormValid"
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
    <div v-else align="right">
      <BButton @click="addMode = true" variant="primary">Adicionar transferencia</BButton>
    </div>
    <div v-if="showCreateSucces" class="alert alert-success">
      Operação realizada com sucesso!
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
            addMode: false,
            submissionError: null,
            showCreateSucces: false,
        }
      },
      methods: {

        validateForm() {
            this.errors = {};
            this.submissionError = null
            if (!this.addNew.originAccount || this.addNew.originAccount.length !== 10) {
                this.errors.originAccount = true;
            }
            if (!this.addNew.targetAccount || this.addNew.targetAccount.length !== 10) {
                this.errors.targetAccount = true;
            }
            if (!this.addNew.transferValue || isNaN(this.removeSpecialCharacters(this.addNew.transferValue))) {
                this.errors.transferValue = true;
            }
            if (!this.addNew.scheduledDate || this.isMoreThan50Days(this.addNew.scheduledDate)) {
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
            
            let newValue = value.replace(/[^0-9,]/g, '');
            
            
            newValue = newValue.replace(',', '.');

            return newValue;
        },

        async addTransfer(){
            const payload = {
                originAccount: parseInt(this.addNew.originAccount, 10),
                targetAccount: parseInt(this.addNew.targetAccount, 10),
                transferValue: parseFloat(this.addNew.transferValue),
                scheduledDate: new Date(this.addNew.scheduledDate)
            };

            this.validateForm()
            if(this.isFormValid){
                this.addNew.transferValue = this.addNew.transferValue.includes(',') ? this.removeSpecialCharacters(this.addNew.transferValue) : this.addNew.transferValue
                configAxios.post('/v1/transfer', payload).then(() => {
                    this.addNew = {}
                    this.$emit('transfer-added')
                    this.showMessage()
                }).catch(() => {
                    this.submissionError = 'Erro ao tentar os parâmetros não aplicam taxa. Tente novamente.'
                })
            }
            
        },

        resetForm() {
            this.addNew = {}
            this.errors = {}
        },

        isMoreThan50Days(scheduledDate) {
            const today = new Date();
            const scheduled = new Date(scheduledDate);

            
            const diffTime = Math.abs(scheduled - today);
            const diffDays = Math.ceil(diffTime / (1000 * 60 * 60 * 24));

            return diffDays > 50;
        },

        showMessage() {
            this.showCreateSucces = true;
            
            setTimeout(() => {
                this.showCreateSucces = false;
            }, 3000);
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
    
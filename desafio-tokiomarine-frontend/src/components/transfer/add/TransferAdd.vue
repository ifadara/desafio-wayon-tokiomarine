<template>
    <div>
        <BForm @submit.prevent="addTransfer">
          <BCard class="border">
            <BRow><BCol 
                        md="3"
                    >
                        <BFormGroup
                            label="Conta de origem:*"
                            label-for="originAccount"
                        >
                            <BFormInput
                                id="originAccount"
                                v-model="addNew.originAccount"
                                placeholder="XXXXXXXXXX"
                                type="number"
                            />
                        </BFormGroup>

                    </BCol>
                    <BCol 
                        md="3"
                    >
                        <BFormGroup
                            label="Conta de destino:*"
                            label-for="targetAccount"
                        >
                            <BFormInput
                                id="targetAccount"
                                v-model="addNew.targetAccount"
                                placeholder="XXXXXXXXXX"
                                type="number"
                            />
                        </BFormGroup>

                    </BCol>
                    <BCol 
                        md="3"
                    >
                        <BFormGroup
                            label="Valor a ser trasnferido:*"
                            label-for="transferValue"
                        >
                            <BFormInput
                                id="transferValue"
                                v-model="addNew.transferValue"
                                :value="formattedValue"
                                @input="updateValue"
                                placeholder="R$ 0,00"
                            />
                        </BFormGroup>

                    </BCol>
                    <BCol 
                        md="3"
                        
                    >
                        <BFormGroup
                            label="Qual a data de agendamento?:*"
                            label-for="scheduledDate"
                        >
                            <BFormInput
                                id="scheduledDate"
                                v-model="addNew.scheduledDate"
                                type="date"
                            />
                        </BFormGroup>

                    </BCol>

            </BRow>

            <BRow>
                <BCol class="text-right mt-2" md="12" align-self="end">
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
      },

      data() {
        return{
            addNew: {},
        }
      },
      methods: {

        formatValue(value) {
            if (!value) return 'R$ 0,00';
            let number = parseFloat(value.replace(/[^\d]/g, '')) / 100;
            return `R$ ${number.toFixed(2).replace('.', ',')}`;
        },

        updateValue(event) {
            const inputValue = event.target.value.replace(/[^\d]/g, '');
            this.addNew.transferValue = (parseFloat(inputValue) / 100).toFixed(2);
        },

        async addTransfer(){
            configAxios.post('/v1/transfer', this.addNew).then(res => {
                this.addNew = res
                return
            }).catch(() => {
                
            })
        }

      }
    }


    </script>
    
    <!-- Add "scoped" attribute to limit CSS to this component only -->
    <style scoped>
    
    </style>
    
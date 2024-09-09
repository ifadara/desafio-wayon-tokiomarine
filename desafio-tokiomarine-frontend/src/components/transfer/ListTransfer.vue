<template>
  <BContainer>
    <BRow>
      <BCol
        md="12"
        class="m-auto mt-5"
      >
        <BCard class="border">
            <div>
              <h1
                align="left"
              >
                Agendar tranferências
              </h1>
              <TransferAdd
                @transfer-added="listTransfers"
              />
            </div>

            <BCard class="border mt-3">
              <BTable
                :items="transfers"
                :fields="tableColumns"
                empty-text="Nenhum resultado encontrado"
                show-empty
                responsive
              >

              </BTable>
            </BCard>
        </BCard>
      </BCol>
    </BRow>
  </BContainer>

  
  
</template>

<script>
import {
  BCard,
  BContainer,
  BRow,
  BCol,
  BTable,
} from 'bootstrap-vue-next'
import TransferAdd from './add/TransferAdd.vue';
import configAxios from '@/configAxios';

export default {
  components: {
    BCard,
    BContainer,
    BRow,
    BCol,
    BTable,
    TransferAdd,
  },

  data(){
    return {
      dadosItens: {
        addMode: true
      },
      transfers: [],


      tableColumns: [
        {key: "originAccount", label: "Conta de Origem"},
        {key: "targetAccount", label: "Conta de Destino"},
        {key: "transferValue", label: "Valor da transferencia"},
        {key: "transferPercentage", label: "Taxa(%)"},
        {key: "feeAmount", label: "Taxa(Valor)"},
        {key: "totalTransferValue", label: "Valor Total"},
        {key: "transferDate", label: "Data de Agendamento"},
        {key: "scheduledDate", label: "Data de tranferencia"},
      ]
    }
    
  },

  async mounted() {
    await this.listTransfers()
  },

  methods: {
    async listTransfers(){
      configAxios.get('v1/transfer').then(res => {
        this.transfers = res.data.content
      })
    }
  },
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

    

</style>

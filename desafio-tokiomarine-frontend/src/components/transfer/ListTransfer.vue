<template>
  <BContainer>
    <BRow>
      <BCol
        md="12"
        class="m-auto mt-5"
      >
        <BCard class="border">
          <div>
            <h1 align="left">Agendar transferências</h1>
            <TransferAdd @transfer-added="listTransfers" />
          </div>

          <BCard class="border mt-3">
            <BTable
              :items="transfers"
              :fields="tableColumns"
              empty-text="Nenhum resultado encontrado"
              show-empty
              responsive
            >
              <template #cell(transferDate)="data">
                {{ formatDate(data.item.transferDate) }}
              </template>

              <template #cell(scheduledDate)="data">
                {{ formatDate(data.item.scheduledDate) }}
              </template>

              <template #cell(transferValue)="data">
                {{ formatDecimal(data.item.transferValue) }}
              </template>

              <template #cell(feeAmount)="data">
                {{ formatDecimal(data.item.feeAmount) }}
              </template>

              <template #cell(totalTransferValue)="data">
                {{ formatDecimal(data.item.totalTransferValue) }}
              </template>
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
} from 'bootstrap-vue-next';
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

  data() {
    return {
      transfers: [],
      tableColumns: [
        { key: "originAccount", label: "Conta de Origem" },
        { key: "targetAccount", label: "Conta de Destino" },
        { key: "transferValue", label: "Valor da Transferência" },
        { key: "transferPercentage", label: "Taxa (%)" },
        { key: "feeAmount", label: "Taxa (Valor)" },
        { key: "totalTransferValue", label: "Valor Total" },
        { key: "transferDate", label: "Data de Agendamento" },
        { key: "scheduledDate", label: "Data de Transferência" },
      ],
    };
  },

  async mounted() {
    await this.listTransfers();
  },

  methods: {
    async listTransfers() {
      configAxios.get('v1/transfer').then((res) => {
        this.transfers = res.data.content;
      });
    },

    formatDate(date) {
      if (!date) return '-';
      const parsedDate = new Date(date);
      const day = String(parsedDate.getDate() + 1).padStart(2, '0');
      const month = String(parsedDate.getMonth() + 1).padStart(2, '0');
      const year = parsedDate.getFullYear();
      return `${day}/${month}/${year}`;
    },

    formatDecimal(value) {
      if (value === null || value === undefined) return '-';
      return value.toFixed(2).replace('.', ',');
    },
  },
};
</script>

<style scoped>

</style>

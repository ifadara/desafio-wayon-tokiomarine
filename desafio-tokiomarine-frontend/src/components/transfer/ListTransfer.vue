<template>
  <BContainer>
    <BRow>
      <BCol
        md="10"
        class="m-auto mt-5"
      >
        <BCard class="border">
            <div>
              <h1
                class="align-self"
              >
                Inicio do teste
              </h1>
            </div>
            <TransferAdd 
              :dados-itens="dadosItens"
            />

            <div>
              <BTable
                :items="transfers"
              >

              </BTable>
            </div>
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
        addMode: false
      }
    }
    
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>

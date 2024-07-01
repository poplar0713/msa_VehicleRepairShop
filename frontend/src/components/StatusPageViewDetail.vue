<template>

    <v-card outlined>
        <v-card-title>
            StatusPage # {{$route.params.id }}
        </v-card-title>

        <v-card-text>
            <div>
                <String label="CustomerName" v-model="item.customerName" :editMode="editMode" @change="change" />
            </div>
            <div>
                <Number label="RequestId" v-model="item.requestId" :editMode="editMode" @change="change" />
            </div>
            <div>
                <Number label="JobId" v-model="item.jobId" :editMode="editMode" @change="change" />
            </div>
            <div>
                <String label="CarNumber" v-model="item.carNumber" :editMode="editMode" @change="change" />
            </div>
            <div>
                <String label="CarId" v-model="item.carId" :editMode="editMode" @change="change" />
            </div>
            <div>
                <String label="JobStatus" v-model="item.jobStatus" :editMode="editMode" @change="change" />
            </div>
            <div>
                <Number label="Totalprice" v-model="item.totalprice" :editMode="editMode" @change="change" />
            </div>
            <div>
                <Date label="RequestDate" v-model="item.requestDate" :editMode="editMode" @change="change" />
            </div>
            <div>
                <String label="MechanicName" v-model="item.mechanicName" :editMode="editMode" @change="change" />
            </div>
        </v-card-text>
    </v-card>

</template>

<script>
  const axios = require('axios').default;

  export default {
    name: 'StatusPageViewDetail',
    props: {
      value: Object,
    },
    data: () => ({
        item : [],
    }),
    async created() {
      var params = this.$route.params;
      var temp = await axios.get(axios.fixUrl('/statusPages/' + params.id))

      this.item = temp.data;

    },
    methods: {
    }
  }
</script>


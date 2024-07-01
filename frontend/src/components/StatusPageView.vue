<template>

    <v-data-table
        :headers="headers"
        :items="statusPage"
        :items-per-page="5"
        class="elevation-1"
    ></v-data-table>

</template>

<script>
    const axios = require('axios').default;

    export default {
        name: 'StatusPageView',
        props: {
            value: Object,
            editMode: Boolean,
            isNew: Boolean
        },
        data: () => ({
            headers: [
                { text: "id", value: "id" },
                { text: "customerName", value: "customerName" },
                { text: "requestId", value: "requestId" },
                { text: "jobId", value: "jobId" },
                { text: "carNumber", value: "carNumber" },
                { text: "carId", value: "carId" },
                { text: "jobStatus", value: "jobStatus" },
                { text: "totalprice", value: "totalprice" },
                { text: "requestDate", value: "requestDate" },
                { text: "mechanicName", value: "mechanicName" },
            ],
            statusPage : [],
        }),
          async created() {
            var temp = await axios.get(axios.fixUrl('/statusPages'))

            temp.data._embedded.statusPages.map(obj => obj.id=obj._links.self.href.split("/")[obj._links.self.href.split("/").length - 1])

            this.statusPage = temp.data._embedded.statusPages;
        },
        methods: {
        }
    }
</script>


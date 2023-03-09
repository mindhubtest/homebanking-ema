const app = Vue.createApp({

    data() {
        return {
            clientes: [],
            name: "",
            lname: "",
            accounts: [],
            transactions: "",
            transactionId: ""

        }

    },

    created() {
        const urlSearchParams = new URLSearchParams(window.location.search);
        const params = Object.fromEntries(urlSearchParams.entries());


        console.log(params.id)
        axios.get("/api/accounts/" + params.id)
            .then(response => {
                console.log(response)
                this.transactions = response.data.transactions
                this.transactions.sort((a, b) => b.id - a.id)
                this.accounts = response.data
                console.log(this.transactions)
            })
            .catch(error => {
                alert(error)
            })

        axios.get("/api/clients/current")
            .then(response => {
                console.log(response)
                this.clientes = response.data
                this.name = this.clientes.firstName
                this.lname = this.clientes.lastName

                console.log(this.accounts)
            })
            .catch(error => {
                res.data
            })


    },
    methods: {
        logout() {
            axios.post('/api/logout')
                .then(response => window.location.href = "/index.html")
                .catch(e => {
                    swal("Not logged");
                });
        }
    }
})


app.mount("#app")
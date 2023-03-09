const app = Vue.createApp({

    data() {
        return {
            clientes: [],
            name: "",
            lname: "",
            transfer: "",
            accounts: [],
            accountDebit: "",
            accountCredit: "",
            amount: "",
            desc: "",
            accountsFiltradas: []
        }

    },

    created() {
        axios.get("/api/clients/current")
            .then(response => {
                console.log(response)
                this.clientes = response.data
                this.name = this.clientes.firstName
                this.lname = this.clientes.lastName
                this.accounts = this.clientes.accounts
                console.log(this.accounts)

            })
            .catch(error => {
                alert(error)
            })


    },
    methods: {
        logout() {
            axios.post('/api/logout')
                .then(response => window.location.href = "/index.html")
                .catch(e => {
                    swal("Not logged");
                });
        },
        transferir() {
            axios.post('/api/transactions', "amount=" + this.amount + "&description=" + this.desc + "&AccOrigen=" + this.accountDebit + "&AccDest=" + this.accountCredit,
            )
                .then(response => {
                    swal({
                        title: "Transfer!",
                        icon: "success",

                    });
                    location.reload()
                })
                .then(response => window.location.href = "/accounts.html")
                .catch(err => swal("Error to realize the transaction, please verify the amount"))
        },
        filtrarCuentas() {
            this.accountsFiltradas = this.accounts.filter(e => e.number != this.accountDebit)

        }


    },


})


app.mount("#app")
const app = Vue.createApp({

    data() {
        return {
            clientes: [],
            name: "",
            lname: "",
            transfer: "",
            loans: [],
            loanId: "",
            loanPayment: "",
            amount: "",
            accounts: "",
            accountDest: ""
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
                this.accounts.sort((a, b) => a.id - b.id)

            })
            .catch(error => {
                alert(error)
            }),
            axios.get("/api/loan")
                .then(response => {
                    console.log(response)
                    this.loans = response.data
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

        completeLoan() {
            axios.post('/api/clients/current/loans',
                { accountNumber: this.accountDest, idLoan: this.loanId, amount: this.amount, payments: this.loanPayment })

                .then(response => {
                    swal({
                        title: "Created!",
                        icon: "success",

                    })
                    window.location.href = "/accounts.html"
                })
                .catch(() => console.log('error')
                )
        }


    },
    computed: {
        loanSeleccionado(id) {
            console.log(loanId)
            return this.loanId = id
        }
    }
})


app.mount("#app")
const app = Vue.createApp({

    data() {
        return {
            clientes: [],
            name: "",
            lname: "",
            accounts: [],
            loans: []
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

                this.loans = response.data.loans
                this.loans.sort((a, b) => a.idClientLoan - b.idClientLoan)
                console.log(this.loans)
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
        AddAccount(type) {
            axios.post('/api/clients/current/accounts', "type=" + type,
                {
                    headers: { 'content-type': 'application/x-www-form-urlencoded' }
                })
            location.reload()
        },
        deleteAccount(id) {
            axios.post("/api/accounts", "number=" + id)
            location.reload()
        }
    },

})


app.mount("#app")

function saltarA(id, tiempo) {
    var tiempo = tiempo || 1000;
    $("html, body").animate({ scrollTop: $(id).offset().top }, tiempo);
}
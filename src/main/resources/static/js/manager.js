const app = Vue.createApp({

    data() {
        return {
            clientes: [],
            Name: "",
            lName: "",
            datos: "",

            firstName: "",
            lastName: "",
        }

    },

    created() {
        axios.get("/rest/clients/current")
            .then(response => {
                console.log(response)
                this.clientes = response.data._embedded.clients
                this.datos = response.data
                this.firstName = this.clientes.firstName
                this.lastName = this.clientes.lastName
            })
            .catch(error => {
                alert(error)
            })


    },
    methods: {
        deleteData(clientes) {
            console.log(clientes._links.self.href)
            axios.delete(clientes._links.self.href)
            location.reload()
        },

        sendClientData: function () {
            axios.post('/rest/clients/', {
                firstName: this.Name,
                lastName: this.lName,
                email: this.mail
            }).then(response => {
                location.reload()
            }).catch(e => {
                console.log(e);
            });
        },
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
const app = Vue.createApp({

    data() {
        return {
            clientes: [],
            name: "",
            lname: "",
            cards: [],
            cardsDebito: [],
            cardsCredito: [],
            showDebit: true,
            showCredit: true,
            type: "",
            color: ""
        }

    },

    created() {
        axios.get("/api/clients/current")
            .then(response => {
                console.log(response)
                this.clientes = response.data
                this.name = this.clientes.firstName
                this.lname = this.clientes.lastName



                this.cards = response.data.cards
                this.cards.sort((a, b) => a.id - b.id)
                console.log(this.cards)
                this.cardsDebito = this.cards.filter(e => e.type == 'DEBIT')
                console.log(this.cardsDebito)
                this.cardsCredito = this.cards.filter(e => e.type == 'CREDIT')
                console.log(this.cardsCredito)
            })
            .catch(function (error) {
                if (error.response) {
                    // The request was made and the server responded with a status code
                    // that falls out of the range of 2xx
                    console.log(error.response.data);
                    console.log(error.response.status);
                    console.log(error.response.headers);
                } else if (error.request) {
                    // The request was made but no response was received
                    // `error.request` is an instance of XMLHttpRequest in the browser and an instance of
                    // http.ClientRequest in node.js
                    console.log(error.request);
                } else {
                    // Something happened in setting up the request that triggered an Error
                    console.log('Error', error.message);
                }
                console.log(error.config);
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
        AddCard() {
            axios.post('/api/clients/current/cards', "cardColor=" + this.color + "&cardType=" + this.type,
                {
                    headers: {
                        'content-type': 'application/x-www-form-urlencoded',
                    },
                }
            )
                .then(response => window.location.href = "/cards.html")
        },
        fecha(fecha) {
            fecha = fecha.split("").splice(0, 7).join("")
            return fecha
        },

        deleteCard(id) {
            axios.put("/api/clients/current/cards", "id=" + id)
                .then(resp => {
                    console.log("borrada")
                    location.reload()
                })
        },
        estaVencida(thruDate) {             //let hoy = new Date(Date.now()).toLocaleString().split(',')[0];  
            let hoy = new Date().toJSON().slice(0, 10).split('-').join('-');
            if (thruDate.valueOf() < hoy.valueOf()) {
                console.log(hoy, thruDate); return true
            }
        }
    },

})


app.mount("#app")
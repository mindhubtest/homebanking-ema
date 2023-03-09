const app = Vue.createApp({

    data() {
        return {
            numberCard: "",
            cvv: "",
            description: "",
            amount: ""
        }

    },

    created() {

    },
    methods: {
        pay() {
            axios.post("/api/payment", {
                number: this.numberCard,
                cvv: this.cvv,
                amount: this.amount,
                description: this.description
            })
                .then(res => console.log("pagado"))
                .then(res => location.reload())
                .catch(res => console.log(res.response.data))
        },
        logout() {
            axios.post('/api/logout')
                .then(response => window.location.href = "/index.html")
                .catch(e => {
                    swal("Not logged");
                });
        },
    }
})


app.mount("#app")
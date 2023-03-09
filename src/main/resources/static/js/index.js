const app = Vue.createApp({

    data() {
        return {

            email: "",
            pwd: "",

            Name: "",
            lName: "",

        }

    },

    created() {


    },
    methods: {
        login() {

            console.log("entro")

            axios.post('/api/login', 'email=' + this.email + '&password=' + this.pwd,
                {
                    headers: { 'content-type': 'application/x-www-form-urlencoded' }
                })
                .then(response => window.location.href = "/accounts.html")
                .catch(e => {
                    swal("¡Password or Email wrong!");
                });
        },
        completarRegistro() {
            axios.post('/api/clients', "firstName=" + this.Name + "&lastName=" + this.lName + "&email=" + this.email + "&password=" + this.pwd,
                {
                    headers: { 'content-type': 'application/x-www-form-urlencoded' }
                })
                .then(response => {
                    console.log('registered')

                    axios.post('/api/login', 'email=' + this.email + '&password=' + this.pwd,
                        {
                            headers: { 'content-type': 'application/x-www-form-urlencoded' }
                        })
                        .then(response => {

                            window.location.href = "/accounts.html"
                        })
                        .catch(e => {
                            swal("¡Password or Email wrong!");
                        });
                })
        }

    },

})


app.mount("#app")


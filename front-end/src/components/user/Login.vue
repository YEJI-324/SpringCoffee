<template>
    <div class="login">
        <div class="container">
            <a href="/#/index"><img src="/imgs/login-logo.png" alt=""></a>
        </div>
        <div class="wrapper">
            <div class="container">
                <div class="login-form">
                    <h3>
                        <span class="checked">로그인</span><span class="separate-line"></span>
                    </h3>
                    <div class="input">
                        <input type="text" placeholder="아이디를 입력하세요" v-model="loginForm.username">
                    </div>
                    <div class="input">
                        <input type="password" placeholder="비밀번호를 입력하세요" v-model="loginForm.password">
                    </div>
                    <div class="vc">
                        <img :src="vcUrl" @click="updateVerifyCode" alt="">
                        <div class="remember">
                            <span>로그인 유지</span><input type="checkbox" v-model="checked">
                        </div>
                    </div>
                    <div class="btn-box">
                        <a href="javascript:;" class="btn" @click="login">로그인</a>
                    </div>
                    <div class="tips">
                        <div class="register" @click="register">회원이 아니신가요?</div>
                    </div>
                </div>
            </div>
        </div>
        <nav-footer></nav-footer>
    </div>
</template>
<script>
    export default {
        name: 'login',
        components: {
        },
        data() {
            return {
                loginForm: {
                    username: '',
                    password: '',
                    code: ''
                },
                checked: true,
                userId: '',
                res: {},
                vcUrl: '/api/verifyCode?time=' + new Date()
            }
        },
        methods: {
            updateVerifyCode() {
                this.vcUrl = '/api/verifyCode?time=' + new Date();
            },
            login() {
                this.axios.post('/user/login', this.loginForm).then((user) => {
                    if (user) {
                        if (this.checked) {
                            this.$cookie.set('userId', user.id, {expires: '1M'});
                        }
                        this.$store.dispatch('saveUsername', user.username);
                        this.res = user;
                        this.$router.push({
                            name: 'index',
                            params: {
                                from: 'login'
                            }
                        });
                    } else {
                        this.vcUrl = '/api/verifyCode?time=' + new Date();
                    }
                }).catch(() => {
                    this.vcUrl = '/api/verifyCode?time=' + new Date();
                });
            },
            register() {
                this.$router.push('/register');
            }
        }
    }
</script>

<style>
</style>
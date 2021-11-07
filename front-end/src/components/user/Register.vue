<template>
    <div class="register">
        <div class="container">
            <a href="/#/index"><img src="/imgs/login-logo.png" alt=""></a>
        </div>
        <div class="wrapper">
            <div class="container">
                <div class="register-form">
                    <h3>
                        <span class="checked">회원가입</span>
                    </h3>
                    <div class="input">
                        <input type="text" placeholder="아이디를 입력해주세요" v-model="registerForm.username">
                        <div class="tips">
                            [ 사용자명 4~16자리, 특수문자 불가능 ]
                        </div>
                    </div>
                    <div class="input">
                        <input type="password" placeholder="비밀번호를 입력해주세요" v-model="registerForm.password">
                        <div class="tips">
                            [ 영문 대소문자 및 숫자 포함 25자리 ]
                        </div>
                    </div>
                    <div class="input">
                        <input type="text" placeholder="e-mail" v-model="registerForm.email">
                        <div class="tips">
                            [ 올바른 이메일 주소를 입력해주세요 ]
                        </div>
                    </div>
                    <div class="btn-box">
                        <a href="javascript:;" class="btn" @click="register">회원가입</a>
                    </div>
                    <div class="tips">
                        <div class="login" @click="login">이미 회원이신가요?<span>로그인</span></div>
                    </div>
                </div>
            </div>
        </div>
        <nav-footer></nav-footer>
    </div>
</template>
<script>
    export default {
        name: 'register',
        components: {
        },
        data() {
            return {
                registerForm: {
                    username: '',
                    password: '',
                    email: ''
                },
            }
        },
        methods: {
            register() {
                if (this.preCheck()) {
                    this.axios.post('/user/register', this.registerForm).then(() => {
                        this.$message.success("注册成功");
                        this.$router.push('/login');
                    });
                }
            },
            preCheck() {
                let username = this.registerForm.username;
                let regUser = /^[a-zA-Z0-9]{4,16}$/;
                if (!regUser.test(username)) {
                    this.$message.warning("用户名格式有误！");
                    return false;
                }

                let password = this.registerForm.password;
                let regPass = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[^]{3,25}$/;
                if (!regPass.test(password)) {
                    this.$message.warning("密码格式有误！");
                    return false;
                }

                let email = this.registerForm.email;
                let regEmail = /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/;
                if (!regEmail.test(email)) {
                    this.$message.warning("邮箱格式有误！");
                    return false;
                }

                return true;
            },
            login() {
                this.$router.push('/login');
            }
        }
    }
</script>
<style>
</style>
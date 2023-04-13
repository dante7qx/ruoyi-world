<template>
  <div class="login">
    
    <el-form ref="loginForm" :model="loginForm" :rules="loginRules" class="login-form">
      <h3 class="title">睿阳RSP开发框架</h3>
      <el-tabs v-model="loginType" @tab-click="switchLoginType" v-if="smsEnabled">
        <el-tab-pane label="账号密码登录" name="uname"></el-tab-pane>
        <el-tab-pane label="短信验证码登录" name="sms"></el-tab-pane>
      </el-tabs>
      <div v-if="loginType === 'uname'">
        <el-form-item prop="username">
          <el-input
            v-model="loginForm.username"
            type="text"
            auto-complete="off"
            autofocus
            ref="username"
            placeholder="账号"
          >
            <svg-icon slot="prefix" icon-class="user" class="el-input__icon input-icon" />
          </el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input
            v-model="loginForm.password"
            type="password"
            auto-complete="off"
            placeholder="密码"
            @keyup.enter.native="handleLogin"
          >
            <svg-icon slot="prefix" icon-class="password" class="el-input__icon input-icon" />
          </el-input>
        </el-form-item>
        <el-form-item prop="code" v-if="captchaEnabled">
          <el-input
            v-model="loginForm.code"
            auto-complete="off"
            placeholder="验证码"
            style="width: 63%"
            @keyup.enter.native="handleLogin"
          >
            <svg-icon slot="prefix" icon-class="validCode" class="el-input__icon input-icon" />
          </el-input>
          <div class="login-code">
            <img :src="codeUrl" alt="" @click="getCode" class="login-code-img"/>
          </div>
        </el-form-item>
        <el-checkbox v-model="loginForm.rememberMe" style="margin:0px 0px 25px 0px;">记住密码</el-checkbox>
      </div>
      <div v-if="loginType === 'sms'">
        <el-form-item prop="userPhone">
          <el-input
            v-model="loginForm.userPhone"
            type="text"
            auto-complete="off"
            ref="userPhone"
            placeholder="手机号"
          >
            <svg-icon slot="prefix" icon-class="phone" class="el-input__icon input-icon" />
          </el-input>
        </el-form-item>
        <el-form-item prop="smsCode" style="margin:0px 0px 25px 0px;">
        <el-input
            v-model="loginForm.smsCode"
            type="text"
            auto-complete="off"
            placeholder="验证码"
          >
            <svg-icon slot="prefix" icon-class="message" class="el-input__icon input-icon" />
            <template slot="append">
              <countdown :key="smsCodeDuration" :end-time="smsCodeDuration">
                <template
                  v-slot:process="anyYouWantedScopName">
                    <span>{{ `${anyYouWantedScopName.timeObj.ceil.s} 秒` }}</span>
                </template>
                <template v-slot:finish>
                    <span @click="sendSmsCode" style="cursor: pointer;">发送验证码</span>
                </template>
              </countdown>
            </template>
          </el-input>
        </el-form-item>
      </div>
      <el-form-item style="width:100%;">
        <el-button
          :loading="loading"
          size="medium"
          type="primary"
          style="width:100%;"
          @click.native.prevent="handleLogin"
        >
          <span v-if="!loading">登 录</span>
          <span v-else>登 录 中...</span>
        </el-button>
        <div style="float: right;" v-if="register">
          <router-link class="link-type" :to="'/register'">立即注册</router-link>
        </div>
      </el-form-item>
    </el-form>
    <!--  底部  -->
    <div class="el-login-footer">
      <span>Copyright © 2014-2023 睿阳科技 All Rights Reserved.</span>
    </div>
  </div>
</template>

<script>
import { getCodeImg, getSmsCode } from "@/api/login"
import Cookies from "js-cookie"
import { encrypt, decrypt } from '@/utils/jsencrypt'
import { validPhoneNumber } from "@/utils/validate"

export default {
  name: "Login",
  data() {
    
    return {
      loginType: "uname",
      codeUrl: "",
      loginForm: {
        username: "",
        password: "",
        rememberMe: false,
        code: "",
        userPhone: "",
        smsCode: "",
        loginType: "",
        uuid: ""
      },
      loginRules: {
        username: [
            { required: true, trigger: "blur", message: "请输入您的账号" }
          ],
          password: [
            { required: true, trigger: "blur", message: "请输入您的密码" }
          ],
          code: [{ required: true, trigger: "change", message: "请输入验证码" }]
      },
      loading: false,
      // 验证码开关
      captchaEnabled: true,
      // 注册开关
      register: false,
      redirect: undefined,
      // 短信开关（若只需要账号登录，将smsEnabled设为false即可，其他不需要修改）
      smsEnabled: true, 
      smsCodeDuration: null
    };
  },
  watch: {
    $route: {
      handler: function(route) {
        this.redirect = route.query && route.query.redirect;
      },
      immediate: true
    }
  },
  created() {
    this.getCode();
    this.getCookie();
  },
  methods: {
    getCode() {
      getCodeImg().then(res => {
        this.captchaEnabled = res.captchaEnabled === undefined ? true : res.captchaEnabled;
        if (this.captchaEnabled) {
          this.codeUrl = "data:image/gif;base64," + res.img;
          this.loginForm.uuid = res.uuid;
        }
      });
    },
    getCookie() {
      const username = Cookies.get("username");
      const password = Cookies.get("password");
      const rememberMe = Cookies.get('rememberMe')
      this.loginForm = {
        username: username === undefined ? this.loginForm.username : username,
        password: password === undefined ? this.loginForm.password : decrypt(password),
        rememberMe: rememberMe === undefined ? false : Boolean(rememberMe)
      };
    },
    sendSmsCode() {
      this.$refs.loginForm.validateField('userPhone', err => {
          if(!err) {
            getSmsCode(this.loginForm.userPhone).then(res => {
              this.$modal.msgSuccess("验证码发送成功，请在2分钟内完成验证！");
              this.smsCodeDuration = new Date().getTime() + 120000
            })
          }
      })
    },
    handleUsernameLogin() {
      if (this.loginForm.rememberMe) {
        Cookies.set("username", this.loginForm.username, { expires: 30 });
        Cookies.set("password", encrypt(this.loginForm.password), { expires: 30 });
        Cookies.set('rememberMe', this.loginForm.rememberMe, { expires: 30 });
      } else {
        Cookies.remove("username");
        Cookies.remove("password");
        Cookies.remove('rememberMe');
      }
    },
    handleSmsLogin() {},
    handleLogin() {
      this.$refs.loginForm.validate(valid => {
        if (valid) {
          this.loginForm.loginType = this.loginType
          this.loading = true;
          if(this.loginType == "uname") {
            this.handleUsernameLogin()
          } else if(this.loginType == "sms") {
            this.handleSmsLogin()
          }
          this.$store.dispatch("Login", this.loginForm).then(() => {
            const uri = this.redirect && this.redirect != '/' ? this.redirect : "/index";
            this.$router.push({path: uri}).catch(() => {});
          }).catch(() => {
            this.loading = false;
            if (this.captchaEnabled) {
              this.getCode();
            }
          });
        }
      });
    },
    switchLoginType(tab, event) {
      let that = this
      this.loginType = tab.name
      this.$refs.loginForm.clearValidate()
      if(this.loginType == 'uname') {
        this.loginRules = {
          username: [
            { required: true, trigger: "blur", message: "请输入您的账号" }
          ],
          password: [
            { required: true, trigger: "blur", message: "请输入您的密码" }
          ],
          code: [{ required: true, trigger: "change", message: "请输入验证码" }]
        }
        setTimeout(()=>{ that.$refs['username'].focus() }, 1)
      } else if(this.loginType == 'sms') {
        const checkUserPhone = (rule, value, callback) => {
            if (!value) {
              return callback(new Error('手机号不能为空'));
            }
            if (validPhoneNumber(value)) {
              callback()
            } else {
              callback(new Error('请输入正确的手机号'));
            }
        };
        this.loginRules = {
          userPhone: [
            { required: true, trigger: "blur", message: "请输入您的手机号" },
            { validator: checkUserPhone, trigger: 'blur' }
          ],
          smsCode: [
            { required: true, trigger: "blur", message: "请输入您的验证码" }
          ]
        }
        setTimeout(()=>{ that.$refs['userPhone'].focus() }, 1)
      }
    },
  }
};
</script>

<style rel="stylesheet/scss" lang="scss">
.login {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
  background-image: url("../assets/images/login-background.jpg");
  background-size: cover;
}
.title {
  margin: 0px auto 30px auto;
  text-align: center;
  color: #707070;
}

.login-form {
  border-radius: 6px;
  background: #ffffff;
  width: 400px;
  padding: 25px 25px 5px 25px;
  .el-input {
    height: 38px;
    input {
      height: 38px;
    }
  }
  .input-icon {
    height: 39px;
    width: 14px;
    margin-left: 2px;
  }
}
.login-tip {
  font-size: 13px;
  text-align: center;
  color: #bfbfbf;
}
.login-code {
  width: 33%;
  height: 38px;
  float: right;
  img {
    cursor: pointer;
    vertical-align: middle;
  }
}
.el-login-footer {
  height: 40px;
  line-height: 40px;
  position: fixed;
  bottom: 0;
  width: 100%;
  text-align: center;
  color: #fff;
  font-family: Arial;
  font-size: 12px;
  letter-spacing: 1px;
}
.login-code-img {
  height: 38px;
}
</style>

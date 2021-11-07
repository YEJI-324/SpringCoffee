<template>
  <div>
    <div class="outterDiv py-5">
      <table class="notice-context">
        <colgroup>
          <col width="15%" />
          <col width="*" />
        </colgroup>
        <tr>
          <th>제목</th>
          <td class="table-title"><input type="text" placeholder="공지글 제목" v-model="title"></td>
        </tr>
        <tr>
          <th>내용</th>
          <td class="table-context"><textarea v-model="content"> 공지글 상세내용</textarea></td>
        </tr>
        <tr>
          <th>이미지</th>
          <input multiple v-on:change="onInputImage()" ref="serveyImage" type="file">
        </tr>
        <tr>
          <th>재고량</th>
          <td class="table-context"><textarea v-model="stockQuantity"> 공지글 상세내용</textarea></td>
        </tr>
        <tr>
          <th>가격</th>
          <td class="table-context"><textarea v-model="price"> 공지글 상세내용</textarea></td>
        </tr>
        <tr>
          <th>카테고리</th>
          <td class="table-context"><textarea v-model="category"> 공지글 상세내용</textarea></td>
        </tr>
      </table>
      <br>
      <div class="BtnWrap">
        <button class="btn btn-success" @click="onSubmit"> 추가 </button>
        <button class="btn btn-success"> 목록으로 가기 </button>
      </div>

    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
    data(){
    return{
      name : '',
      content : '',
      image: '',
      stockQuantity: '',
      price: '',
      category: '',
    }
  },
  methods:{
    onSubmit() {
      const formData = new FormData;
      
      formData.append('name', this.name);
      formData.append('content', this.content);
      formData.append('image', this.image);
      formData.append('stockQuantity', this.stockQuantity);
      formData.append('price', this.price);
      formData.append('category', this.category)

      for(let key of formData.entries()) {
        console.log(`${key}`);
      }

      axios.post( '/v2/register', formData, {
          headers: {
              'Content-Type': 'multipart/form-data'
          }
        }).then(function(){
          console.log('SUCCESS!!');
        })
        .catch(function(){
          console.log('FAILURE!!');
      });
    },
    onInputImage() {
      this.image = this.$refs.serveyImage.files[0];
      console.log("this.input.image")
    },
    
  }
};

</script>

<style scoped>
</style>
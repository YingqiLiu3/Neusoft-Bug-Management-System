<!-- 主页面的布局 -->
<template>
    <div class="common-layout">
      <el-container class="app-wrapper">
        <!-- 伸缩导航 -->
        <el-aside :width="asideWidth" class="sidebar-container">
          <Menu />
        </el-aside>
        <!-- 伸缩主页 -->
        <el-container class="container" :class="{hidderContainer:!$store.getters.siderType}">
          <el-header><Headers/></el-header>
          <el-main>
            <!-- <router-view> 标签作为占位符，路由的视图（或组件）将会被渲染到这个位置 -->
            <router-view></router-view>
          </el-main>
        </el-container>
      </el-container>
    </div>
</template> 

<script setup>
import Menu from './Menu'
import Headers from './headers'
import { useStore } from 'vuex';
import { computed } from 'vue';

const store= useStore()
const asideWidth = computed(() => {
  return store.getters.siderType?"210px":"67px"
})

</script>

<style lang="scss" scoped>

.app-container {
  position: relative;
  width: 100%;
  height: 100%;
}
.container {
    //采用定义好的变量值，在style/variables.scss中定义，需要导入
  width: calc(100% - $sideBarWidth);
  height: 100%;

  position: fixed;
  top: 0;
  right: 0;
  z-index: 9;
  transition: all 0.28s;
  &.hidderContainer {
    width: calc(100% - $hideSideBarWidth);
  }
}
::v-deep .el-header {
  padding: 0;
}
</style>

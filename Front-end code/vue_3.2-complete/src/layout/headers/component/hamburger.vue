<template>
    <div class="hamburger-container" @click="Sider">
        <svg-icon :icon="icon"></svg-icon>
    </div>
</template>

<script setup>
import { useStore } from 'vuex';
import { computed } from 'vue';

const store = useStore();
const Sider=()=>{
    store.commit('app/changeSiderType')
}
const icon = computed(()=>{
    return store.getters.siderType?"hamburger-opened":"hamburger-closed"
})

</script>

<style lang="scss" scoped>
.hamburger-container {
    background: transparent;
    margin-right: 16px;
    box-sizing: border-box;
    cursor:pointer;
}
</style>

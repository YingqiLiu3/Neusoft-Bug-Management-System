<template>
  <el-card>
    <el-row :gutter="20" class="header">
      <el-select v-model="projectSelect" filterable placeholder="请选择" @change="initGetBugs">
        <el-option
          v-for="item in projectOptions"
          :key="item.projectId"
          :label="item.projectName"
          :value="item.projectId">
        </el-option>
      </el-select>
    </el-row>

    <div class="echart" ref="myChart1" id="myChart1" :style="{ float: 'left', width: '50%', height: '400px' }"></div>
    <div class="echart" ref="myChart2" id="myChart2" :style="{ float: 'left', width: '50%', height: '400px' }"></div>
  </el-card>
</template>

<script setup>
import { Search,Delete,Edit } from '@element-plus/icons-vue';
import {ref, onMounted} from 'vue'
import {getBugs} from '@/api/bugs'
import {getProjects} from '@/api/projects'
import * as echarts from 'echarts'

const queryForm = ref({
  query: '',
})

const tableData = ref([])

// 下拉菜单获取项目列表
const projectSelect = ref()
const projectOptions = ref([])

// 初始化项目列表
const initGetProjects = async() => {
  const res = await getProjects(queryForm.value)
  projectOptions.value = res
  console.log(projectOptions.value)
}

const form = ref({
  size: 1000,
  current: 1
})

// let currentPage = 1
// let currentTotal = 1

const tableDataBugs = ref([])  
const bugList = ref([])  // 存储具体的Bug列表，用于后续数据处理

// 初始化Bug数据
const initGetBugs = async(val) => {
  console.log(val)
  // form.value.pageNum = currentPage
  tableDataBugs.value = await getBugs(val, form.value)
  console.log(tableDataBugs.value)
  // currentPage = tableDataBugs.value.current
  // currentTotal = tableDataBugs.value.total
  bugList.value = tableDataBugs.value.records
  console.log('初始化bug请求已经发送', bugList.value)
  setPieData()
  initDate()
  initEcharts()
}

initGetProjects()

let myChart1 = {}
let myChart2 = {}



const pieData1 = [
  {
    value: 0,
    name: "高"
  },
  {
    value: 0,
    name: "中"
  },
  {
    value: 0,
    name: "低"
  }
]
const pieData2 = [
{
    value: 0,
    name: "新创建"
  },
  {
    value: 0,
    name: "待修改"
  },
  {
    value: 0,
    name: "修改中"
  },
  {
    value: 0,
    name: "已修改"
  },
  {
    value: 0,
    name: "待协商"
  },
  {
    value: 0,
    name: "已完成"
  },
  {
    value: 0,
    name: "暂不修改"
  },
  {
    value: 0,
    name: "永不修改"
  }
]

const setPieData = () => {
  // 初始化pieData
  for (let n = 0; n < pieData1.length; n++) {
    pieData1[n].value = 0
  }
  for (let n = 0; n < pieData2.length; n++) {
    pieData2[n].value = 0
  }
  // 根据 bug 状态统计数据
  for (let i = 0; i < bugList.value.length; i++) {
    switch (bugList.value[i].bugGrade) {
      case '高':
        pieData1[0].value += 1
        break
      case '中':
        pieData1[1].value += 1
        break
      case '低':
        pieData1[2].value += 1
        break
    }
    switch (bugList.value[i].bugState) {
      case "新创建":
        pieData2[0].value += 1;
        break;
      case "待修改":
        pieData2[1].value += 1;
        break;
      case "修改中":
        pieData2[2].value += 1;
        break;
      case "已修改":
        pieData2[3].value += 1;
        break;
      case "待协商":
        pieData2[4].value += 1;
        break;
      case "已完成":
        pieData2[5].value += 1;
        break;
      case "暂不修改":
        pieData2[6].value += 1;
        break;
      case "永不修改":
        pieData2[7].value += 1;
        break;
    }
  }
}

const pieName1 = []
const pieName2 = []
const myChartStyle = { float: "left", width: "100%", height: "400px" } //图表样式

onMounted(() => {
  initDate() // 初始化图标名称数据
  initEcharts()  // 初始化图表
})

const initDate = () => {  // 初始化pieName
  for (let i = 0; i < pieData1.length; i++) {
    // xData[i] = i;
    // yData =xData[i]*xData[i];
    pieName1[i] = pieData1[i].name
  }

  for (let j = 0; j < pieData2.length; j++) {
    pieName2[j] = pieData2[j].name
  }
}

const initEcharts = () => {
  // 饼图
  const option1 = {
    legend: {
      // 图例
      data: pieName1,
      right: "10%",
      top: "30%",
      orient: "vertical"
    },
    title: {
      // 设置饼图标题，位置设为顶部居中
      text: "项目Bug严重度分布统计",
      top: "0%",
      left: "center"
    },
    color: ['#f5583f', '#f0d928', '#00e69d'],
    series: [
      {
        type: "pie",
        label: {
          show: true,
          formatter: "{b} : {c} ({d}%)" // b代表名称，c代表对应值，d代表百分比
        },
        radius: ["30%", "70%"], //饼图半径
        data: pieData1
      }
    ]
  }

  const option2 = {
    legend: {
      // 图例
      data: pieName2,
      right: "5%",
      top: "15%",
      orient: "vertical"
    },
    title: {
      // 设置饼图标题，位置设为顶部居中
      text: "项目Bug工作状态统计",
      top: "0%",
      left: "center"
    },
    color: [
    "#f5583f", "#f0d928", "#00e69d", "#0099ff", 
    "#ff66cc", "#9933ff", "#ff9933", "#669966",
    ],
    series: [
      {
        type: "pie",
        label: {
          show: true,
          formatter: "{b} : {c} ({d}%)" // b代表名称，c代表对应值，d代表百分比
        },
        radius: ["30%", "70%"], //饼图半径
        data: pieData2
      }
    ]
  }

  // console.log(this.seriesData)
  /* const optionFree = {
    xAxis: {},
    yAxis: {},
    series: [
      {
        data: this.seriesData,
        type: "line",
        smooth: true
      }
    ]
  } */
  myChart1 = echarts.init(document.getElementById("myChart1"));
  myChart1.setOption(option1)
  myChart2 = echarts.init(document.getElementById("myChart2"));
  myChart2.setOption(option2)
  // 随着屏幕大小调节图表
  window.addEventListener("resize", () => {
    myChart1.value.resize()
    myChart2.value.resize()
  })
}



</script>




<style lang="scss" scoped>
.header{
  padding-bottom:24px;
  box-sizing: border-box;
}
</style>

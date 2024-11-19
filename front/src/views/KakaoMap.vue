<script setup>
import { ref, watch, onMounted } from 'vue'

const mapContainer = ref(null)
let mapInstance = null 
let markers = [] 


const props = defineProps({
  markersData: {
    type: Array,
    default: () => [],
  },
})


const addMarker = (position, title) => {
  const marker = new window.kakao.maps.Marker({
    position,
    title,
    map: mapInstance,
  })
  markers.push(marker)
}


const clearMarkers = () => {
  markers.forEach(marker => marker.setMap(null))
  markers = []
}


const updateMarkers = () => {
  clearMarkers()
  props.markersData.forEach(markerData => {
    const position = new window.kakao.maps.LatLng(markerData.lat, markerData.lng)
    addMarker(position, markerData.title)
  })
}


const loadKakaoMap = (container) => {
  const script = document.createElement('script')
  script.src = `https://dapi.kakao.com/v2/maps/sdk.js?appkey=2eb7f968d57a403ededbca7a941a1229&autoload=false`
  document.head.appendChild(script)

  script.onload = () => {
    window.kakao.maps.load(() => {
      const options = {
        center: new window.kakao.maps.LatLng(37.501426, 127.039567), 
        level: 3, 
        maxLevel: 5, 
      }
      mapInstance = new window.kakao.maps.Map(container, options) 
      updateMarkers() 
    })
  }
}


watch(() => props.markersData, updateMarkers)


onMounted(() => {
  loadKakaoMap(mapContainer.value)
})
</script>

<template>
  <div ref="mapContainer" style="width: 100%; height: 100%"></div>
</template>

<style scoped>
</style>

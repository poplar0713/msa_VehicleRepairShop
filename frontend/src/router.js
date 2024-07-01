
import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router);


import ReceiptReceiptManager from "./components/listers/ReceiptReceiptCards"
import ReceiptReceiptDetail from "./components/listers/ReceiptReceiptDetail"

import ShopShopManager from "./components/listers/ShopShopCards"
import ShopShopDetail from "./components/listers/ShopShopDetail"

import VehiclepartsVehiclePartsManager from "./components/listers/VehiclepartsVehiclePartsCards"
import VehiclepartsVehiclePartsDetail from "./components/listers/VehiclepartsVehiclePartsDetail"


import MechanicMechanicManager from "./components/listers/MechanicMechanicCards"
import MechanicMechanicDetail from "./components/listers/MechanicMechanicDetail"


export default new Router({
    // mode: 'history',
    base: process.env.BASE_URL,
    routes: [
            {
                path: '/receipts/receipts',
                name: 'ReceiptReceiptManager',
                component: ReceiptReceiptManager
            },
            {
                path: '/receipts/receipts/:id',
                name: 'ReceiptReceiptDetail',
                component: ReceiptReceiptDetail
            },

            {
                path: '/shops/shops',
                name: 'ShopShopManager',
                component: ShopShopManager
            },
            {
                path: '/shops/shops/:id',
                name: 'ShopShopDetail',
                component: ShopShopDetail
            },

            {
                path: '/vehicleparts/vehicleParts',
                name: 'VehiclepartsVehiclePartsManager',
                component: VehiclepartsVehiclePartsManager
            },
            {
                path: '/vehicleparts/vehicleParts/:id',
                name: 'VehiclepartsVehiclePartsDetail',
                component: VehiclepartsVehiclePartsDetail
            },


            {
                path: '/mechanics/mechanics',
                name: 'MechanicMechanicManager',
                component: MechanicMechanicManager
            },
            {
                path: '/mechanics/mechanics/:id',
                name: 'MechanicMechanicDetail',
                component: MechanicMechanicDetail
            },



    ]
})

import React, {FunctionComponent, NamedExoticComponent} from 'react';
import {BrowserRouter, Route, Routes} from 'react-router-dom';
import {ProductList} from "../components/Product/ProductList";
import {DesktopOutlined, PieChartOutlined, UserOutlined} from "@ant-design/icons";
import {KitchenMenu} from "../components/Menu/KitchenMenu";
import {Order} from "../components/Order/Order";
import {Delivery} from "../components/Delivery/Delivery";
import {KitchenForceMain} from "../components/Main/KitchenForceMain";

interface MainRoute {
    key: string;
    title: string;
    path: string;
    icon: React.ReactNode;
    imageUrl: string;
    component : React.ReactNode;
    subRouteList : SubRoute[];
}

interface SubRoute {
    key: string;
    title: string;
    path: string;
    component: React.ReactNode
}

export const routeList : MainRoute[] = [
    {
        key: 'product-router',
        title: '상품',
        path: '/product',
        imageUrl: 'https://cdn0.iconfinder.com/data/icons/cosmo-layout/40/box-512.png',
        icon: <PieChartOutlined/>,
        component: <ProductList/>,
        subRouteList: []
    },
    {
        key: 'menu-router',
        title: '메뉴',
        path: '/menu',
        imageUrl: 'https://cdn-icons-png.flaticon.com/512/151/151409.png',
        icon: <DesktopOutlined/>,
        component: <KitchenMenu/>,
        subRouteList: []
    },
    {
        key: 'order-router',
        title: '주문',
        path: '/order',
        icon: <UserOutlined/>,
        component: <Order/>,
        imageUrl: 'https://cdn-icons-png.flaticon.com/512/849/849588.png',
        subRouteList: [
            {
                key: 'table-order',
                path: '/table',
                title: '테이블 주문',
                component : <Order/>,

            },
            {
                key: 'delivery-order',
                path: '/delivery',
                title: '배달 주문',
                component : <Order/>,

            }
        ]
    },
    {
        key: 'delivery-router',
        title: '배달',
        path: '/delivery',
        imageUrl: 'https://www.kindpng.com/picc/m/720-7200355_delivery-delivery-pictogram-free-png-transparent-png.png',
        icon: <UserOutlined/>,
        component: <Delivery/>,
        subRouteList: []
    }
]

const MainRouter = () => (
    <div>
        <Routes>
            <Route path = '/' element={<KitchenForceMain/>} />
            {
                routeList.map((route)=> {
                    let resultRoute;

                    if(route.subRouteList.length>1) {
                        resultRoute = (
                            route.subRouteList.map((subRoute) => (
                                <Route
                                    key={subRoute.key}
                                    path={route.path + subRoute.path}
                                    element={subRoute.component}
                                >
                                </Route>
                            ))
                        );
                    }
                    else
                    {
                        resultRoute = (
                            <Route
                                key={route.key}
                                path={route.path}
                                element={route.component}
                            >
                            </Route>
                        );
                    }

                    return resultRoute;
                })
            }
        </Routes>
    </div>
)

export default MainRouter;

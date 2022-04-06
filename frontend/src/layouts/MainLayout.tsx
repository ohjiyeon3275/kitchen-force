import {Layout, Menu, Breadcrumb} from "antd";
import {useState} from "react";
import 'antd/dist/antd.css';
import MainRouter, {routeList} from "./MainRouter";
import {Link} from "react-router-dom";

const { Header, Content, Footer, Sider } = Layout;
const { SubMenu } = Menu;


type LayoutProps = {
    title: string,
}

const MainLayout = ({title}:LayoutProps) => {
    const [collapsed, setCollapsed] = useState<boolean>(false)
    const [productT, setLoginFormToggle] = useState<boolean>(true)

    const onCollapse = (collapsed : boolean) => {
        console.log(collapsed);
        setCollapsed(collapsed);
    };

    return (
        <Layout style={{ minHeight: '100vh' }}>
            <Sider collapsible collapsed={collapsed} onCollapse={onCollapse}>
                <div className="logo" />
                <Menu defaultSelectedKeys={['1']} theme="dark" mode="inline">
                    {
                        routeList.map((route) => {
                            let menuComponent;
                            if (route.subRouteList.length > 1)
                                menuComponent = (

                                        <SubMenu key={route.key} icon={route.icon} title={route.title} >
                                            {
                                                route.subRouteList.map((subRoute) => (
                                                    <Menu.Item key={subRoute.key}>
                                                        <Link to={route.path + subRoute.path}>{subRoute.title}</Link>
                                                    </Menu.Item>
                                                ))
                                            }
                                        </SubMenu>
                                );
                            else
                                menuComponent = (
                                    <Menu.Item key={route.key} icon={route.icon}>
                                        <Link to={route.path}>{route.title}</Link>
                                    </Menu.Item>
                                )
                            console.log(menuComponent);
                            return menuComponent;
                        })
                    }


                    {/*<KitchenMenu.Item key="1" icon={<PieChartOutlined />}>*/}
                    {/*    상품*/}
                    {/*</KitchenMenu.Item>*/}
                    {/*<KitchenMenu.Item key="2" icon={<DesktopOutlined />}>*/}
                    {/*   메뉴*/}
                    {/*</KitchenMenu.Item>*/}
                    {/*<SubMenu key="sub1" icon={<UserOutlined />} title="주문">*/}
                    {/*    <KitchenMenu.Item key="3">테이블 주문</KitchenMenu.Item>*/}
                    {/*    <KitchenMenu.Item key="4">배달 주문</KitchenMenu.Item>*/}
                    {/*</SubMenu>*/}
                    {/*<KitchenMenu.Item key="9" icon={<UserOutlined />}>*/}
                    {/*    배달*/}
                    {/*</KitchenMenu.Item>*/}
                </Menu>
            </Sider>
            <Layout className="site-layout">
                <Header className="site-layout-background" style={{ paddingLeft: 10}}><h1>{title}</h1></Header>
                <Content style={{ margin: '0 16px' }}>
                    <MainRouter/>
                    <Breadcrumb style={{ margin: '16px 0' }}>
                        <Breadcrumb.Item>User</Breadcrumb.Item>
                        <Breadcrumb.Item>Bill</Breadcrumb.Item>
                    </Breadcrumb>
                </Content>
                <Footer style={{ textAlign: 'center' }}>Ant Design ©2018 Created by Ant UED</Footer>
            </Layout>
        </Layout>
    );
}

export default MainLayout;

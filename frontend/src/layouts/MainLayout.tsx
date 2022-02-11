import {Layout, Menu, Breadcrumb} from "antd";
import {
    DesktopOutlined,
    PieChartOutlined,
    FileOutlined,
    TeamOutlined,
    UserOutlined,
} from "@ant-design/icons";

import {useState} from "react";
import 'antd/dist/antd.css';
import {showNotification} from "../utils/utils";
import MainRouter from "./MainRouter";
import {ProductList} from "../components/Product/ProductList";

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
                <Menu theme="dark" defaultSelectedKeys={['1']} mode="inline">
                    <Menu.Item key="1" icon={<PieChartOutlined />}>
                        상품
                    </Menu.Item>
                    <Menu.Item key="2" icon={<DesktopOutlined />}>
                       메뉴
                    </Menu.Item>
                    <SubMenu key="sub1" icon={<UserOutlined />} title="주문">
                        <Menu.Item key="3">테이블 주문</Menu.Item>
                        <Menu.Item key="4">배달 주문</Menu.Item>
                    </SubMenu>
                    <Menu.Item key="9" icon={<UserOutlined />}>
                        배달
                    </Menu.Item>
                </Menu>
            </Sider>
            <Layout className="site-layout">
                <Header className="site-layout-background" style={{ paddingLeft: 10}}><h1>{title}</h1></Header>
                {/*<MainRouter/>*/}
                <ProductList/>
                <Content style={{ margin: '0 16px' }}>
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

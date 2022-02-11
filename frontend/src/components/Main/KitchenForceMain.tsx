import {Card, Carousel, Layout, Menu, PageHeader, Space} from "antd";
import React from "react";
import {HistoryOutlined, PoweroffOutlined, SmileOutlined, ThunderboltOutlined} from "@ant-design/icons";
import Meta from "antd/es/card/Meta";
import {routeList} from "../../layouts/MainRouter";
import {Link} from "react-router-dom";

const contentStyle = {
    height: '500px',
    color: '#fff',
    textAlign: 'center',
    background: '#364d79',
} as React.CSSProperties;


export const KitchenForceMain = () => {
    return (
        <div>
            <Layout className="kitchen-main-layout">
                <PageHeader
                    className="site-page-header"
                    onBack={() => null}
                    title="Kitchen-Force Main"
                    subTitle="메인 화면 입니다."
                />

                <Space direction={"vertical"} align={"center"}>
                    <Space direction={"horizontal"} align={"center"}>
                        {
                            routeList.map((route) => {
                                const menuComponent = (
                                    <Link to={route.path}>
                                        <Card
                                            hoverable
                                            style={{ width: 250}}
                                            cover={<img alt="example" src={route.imageUrl} height={250} />}
                                        >
                                            <Meta title={route.title}/>
                                        </Card>
                                    </Link>
                                )
                                console.log(menuComponent);
                                return menuComponent;
                            })
                        }
                    </Space>
                    <Space direction={"horizontal"} align={"center"}>
                        <Carousel style={{width:'950px'}} autoplay>
                            <div>
                                <h3 style={contentStyle}>1</h3>
                            </div>
                            <div>
                                <PoweroffOutlined />
                                <h3 style={contentStyle}>2</h3>
                            </div>
                            <div>
                                <SmileOutlined />
                                <h3 style={contentStyle}>3</h3>
                            </div>
                            <div>
                                <ThunderboltOutlined />
                                <h3 style={contentStyle}>4</h3>
                            </div>
                        </Carousel>
                    </Space>
                </Space>
            </Layout>
        </div>
    )
}

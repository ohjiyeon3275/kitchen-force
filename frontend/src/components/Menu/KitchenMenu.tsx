import {Button, Checkbox, Dropdown, Input, Menu, Modal, PageHeader, Space, Table, Tag} from "antd";
import {DollarCircleOutlined, DownOutlined, InboxOutlined, UserOutlined} from "@ant-design/icons";
import {ColumnProps} from "antd/es/table";
import {MenuGroupResponse, MenuResponse, MenuTableData, ProductResponse} from "../../apis/interfaces";
import {createProduct} from "../../apis/api";
import {showNotification} from "../../utils/utils";
import {Key, useState} from "react";
import {PresetColorTypes, PresetStatusColorType} from "antd/lib/_util/colors";

export const KitchenMenu = () => {

    const [selectedRowKeys, setSelectedRowKeys] = useState<Key[]>([]);
    const [selectedItem, setSelectedItem] = useState<MenuTableData | null>(null);

    const [createModalVisibility, setCreateModalVisibility] = useState(false);
    const [isUpdate, setIsUpdate] = useState(false);

    const [loading, setLoading] = useState(false);

    const [innerModalVisibility, setInnerModalVisibility] = useState(false);
    const [innerLoading, setInnerLoading] = useState(false);

    const [newMenuName, setNewMenuName] = useState<string | null>(null);
    const [newMenuPrice, setNewMenuPrice] = useState(0);
    const [newMenuGroup, setNewMenuGroup] = useState(0);
    const [newMenuProducts, setNewMenuProducts] = useState(0);
    const [newMenuHidden, setNewMenuHidden] = useState<boolean>(false);

    const productDropDownList = (
        <Menu onClick={handleMenuClick}>
            <Menu.Item key="1" icon={<UserOutlined/>}>
                그룹 1
            </Menu.Item>
            <Menu.Item key="2" icon={<UserOutlined/>}>
                그룹 2
            </Menu.Item>
            <Menu.Item key="3" icon={<UserOutlined/>}>
                그룹 3
            </Menu.Item>
        </Menu>
    );

    // const onSelectChange = (selectedRowKeys : Key[], selectedRows: MenuTableData[]) => {
    //     console.log('selectedRowKeys changed: ', selectedRowKeys);
    //     console.log('selectedRows changed: ', selectedRows);
    //     setSelectedRowKeys(selectedRowKeys)
    // };


    const tRowSelection = {
        onChange: (selectedRowKeys: React.Key[], selectedRows: MenuTableData[]) => {
            console.log(`selectedRowKeys: ${selectedRowKeys}`, 'selectedRows: ', selectedRows);
            setSelectedRowKeys(selectedRowKeys);
            setSelectedItem(selectedRows[0]);
        },
    };

    function handleMenuClick(e: any) {
        console.log('click', e);
    }

    const showCreateModal = (isUpdate: boolean) => {
        setIsUpdate(isUpdate);
        setCreateModalVisibility(true);
    };

    const handleOk = () => {
        showNotification(
            'warning',
            '준비중입니다.',
            '준비중입니다.')

        setLoading(true);
        setLoading(false);
        setCreateModalVisibility(false);
    };

    const handleCancel = () => {
        setCreateModalVisibility(false);
    };


    const columns: ColumnProps<MenuTableData>[] = [
        {
            title: 'Menu-ID',
            dataIndex: 'id',
            key: 'id',
        },
        {
            title: '메뉴 이름',
            dataIndex: 'name',
            key: 'name',
        },
        {
            title: '메뉴 가격',
            dataIndex: 'price',
            key: 'price',
        },
        {
            title: '메뉴 숨겨짐 유무',
            dataIndex: 'isHidden',
            key: 'isHidden',
            render : isHidden => (
                <Tag color={isHidden ? 'red' : 'green'}>
                    {isHidden ? '숨겨짐' : '보여짐'}
                </Tag>
            )
        },
        {
            title: '메뉴 그룹 정보',
            dataIndex: 'menuGroup',
            key: 'menuGroup',
            render: menuGroup => (
                <span>
                {
                    menuGroup.map((menuGroup : MenuGroupResponse)  => {
                        let color =  PresetColorTypes.at((menuGroup.id % PresetColorTypes.length));
                        return (
                            <Tag color={color} key={menuGroup.id}>
                                {menuGroup.name}
                            </Tag>
                        );
                    })
                }
                </span>
            )
        },
    ];

    // TODO : sample data
    const sampleData = [
        {
            key: 1,
            id: 1,
            name : "짜장면",
            price : 1200,
            isHidden : false,
            menuGroup : [
                {
                    id : 1,
                    name : "세트 1"
                },
                {
                    id : 2,
                    name : "세트 2"
                },
            ],
        },
        {
            key: 2,
            id: 2,
            name : "탕수육",
            price : 12000,
            isHidden : false,
            menuGroup : [
                {
                    id : 1,
                    name : "세트 1"
                },
                {
                    id : 2,
                    name : "세트 2"
                },
            ],
        },
        {
            key: 3,
            id: 3,
            name : "짬뽕",
            price : 2200,
            isHidden : false,
            menuGroup : [
                {
                    id : 1,
                    name : "세트 1"
                },
                {
                    id : 2,
                    name : "세트 2"
                },
            ],
        },
    ]

    return (
        <div>
            <PageHeader
                className="site-page-header"
                onBack={() => null}
                title="준비중입니다."
                subTitle="Menu 화면 입니다."
            />
            <div className="space-align-block">
                <Space direction={"horizontal"} align={"end"}>
                    <Button key="menu_register" onClick={()=>showCreateModal(false)} >신규 등록</Button>
                    <Button key="menu_modify" onClick={()=>{
                        console.log("selectedRowKey : " + selectedRowKeys.length);
                        if(selectedRowKeys.length === 0){
                            showNotification('warning',
                                '수정할 수 없습니다.',
                                '수정할 항목을 선택해주세요');
                            return;
                        }
                        showCreateModal(true)
                    }} >수정</Button>
                    <Button key="menu_deletion" >삭제</Button>
                </Space>
            </div>

            <Table
                key = "menu_list"
                className = "menu_list"
                rowSelection={
                    {
                        type: 'radio',
                        ...tRowSelection
                    }
                }
                rowKey={(menu): number => menu.id}
                columns = {columns}
                tableLayout="auto"
                bordered={true}
                dataSource={sampleData}
            />

            <Modal
                visible={createModalVisibility}
                title="신규 메뉴 등록 / 수정"
                onOk={handleOk}
                onCancel={handleCancel}
                footer={[
                    <Button key="back" onClick={handleCancel}>
                        Return
                    </Button>,
                    <Button key="submit" type="primary" loading={loading} onClick={handleOk}>
                        Submit
                    </Button>,
                ]}
            >
                <Space direction={"vertical"}>
                    <Dropdown overlay={productDropDownList}>
                        <Button>
                            메뉴 그룹 선택 <DownOutlined />
                        </Button>
                    </Dropdown>
                    <Input
                        addonBefore="메뉴 이름"
                        placeholder="메뉴 이름을 입력하세요"
                        prefix={<InboxOutlined/>}
                        value = {(selectedItem!=null && isUpdate)? selectedItem.name : ''}
                        onChange={(e:React.ChangeEvent<HTMLInputElement>)=>{
                            console.log("메뉴 이름 입력 : " + e.currentTarget.value);
                            setNewMenuName(e.currentTarget.value)
                        }}/>
                    <Input
                        addonBefore="메뉴 가격"
                        placeholder="메뉴 가격을 입력하세요"
                        value = {(selectedItem!=null && isUpdate)? selectedItem.price : ''}
                        prefix={<DollarCircleOutlined/>}
                        onChange={(e:React.ChangeEvent<HTMLInputElement>)=>{
                            console.log("메뉴 가격 입력 : " + e.currentTarget.value);
                            setNewMenuPrice(Number(e.currentTarget.value))
                        }}/>
                    <Checkbox
                        checked ={(selectedItem!=null && isUpdate)? selectedItem.isHidden : false}
                        onChange={(e)=>setNewMenuHidden(e.target.checked)}
                    >
                        숨기기
                    </Checkbox>
                </Space>
            </Modal>
        </div>
    )
}

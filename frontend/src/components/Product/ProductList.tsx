import {Table, Tag, Space, Modal, Button, Input} from 'antd';
import {ColumnProps} from "antd/es/table";
import {useState} from "react";
import {ProductRequest, ProductResponse} from "../../apis/interfaces";
import {createProduct, getProductListApi} from "../../apis/api";
import CommonPageHeader from "../../layouts/CommonPageHeader";
import {showNotification} from "../../utils/utils";
import {DollarCircleOutlined, InboxOutlined} from "@ant-design/icons";


export const ProductList = () => {
    const [data, setData] = useState<ProductResponse[]>([]);
    const [createModalVisibility, setCreateModalVisibility] = useState(false);
    const [newProductRequest, setNewProductRequest] = useState<ProductRequest>({});

    const [newProductName, setNewProductName] = useState<string|null>(null);
    const [newProductPrice, setNewProductPrice] = useState(0);

    const [loading, setLoading] = useState(false);

    const columns : ColumnProps<ProductResponse>[] = [
        {
            title: 'ProductId',
            dataIndex: 'id',
            key: 'id',
        },
        {
            title: 'ProductName',
            dataIndex: 'name',
            key: 'name',
        },
        {
            title: 'Price',
            dataIndex: 'price',
            key: 'price',
        },
    ];

    const onChange = async() : Promise<void> => {
        try{
            const response = await getProductListApi();
            setData(response);
            console.log(response);
        } catch (error) {
            // @ts-ignore
            showNotification('error', '조회 요청에 실패하였습니다.', `[${error.response.data}]`);
        }
    }

    const onExpand = async (expanded: boolean, record: ProductResponse) => {
        try {
            const response = await getProductListApi();
            setData(response);
        } catch (error) {
            // @ts-ignore
            showNotification('error', '조회 요청에 실패하였습니다.', `[${error.response.data}]`);
        }
    }

    const showCreateModal = () => {
        setCreateModalVisibility(true);
    };

    const handleOk = () => {
        setLoading(true);
        // setNewProductRequest({name:newProductName, price: newProductPrice})
        console.log("currentNewProductRequest : " + newProductRequest.name)
        console.log("currentNewProductRequest : " + newProductRequest.price)
        createProduct(newProductName, newProductPrice)
            .then(()=>showNotification(
                'info',
                '등록에 성공하였습니다.',
                '성공.'))
            .catch((error) =>
                showNotification(
                    'error',
                    `${error.response.data.errorCode}`,
                    `${error.response.data.errorMessage}`)
            )
        setLoading(false);
        setCreateModalVisibility(false);
        // setTimeout(() => {
        //     createProduct(newProductName, newProductPrice)
        //         .then(()=>showNotification(
        //             'info',
        //             '등록에 성공하였습니다.',
        //             '성공.'))
        //         .catch((error) =>
        //             showNotification(
        //                 'error',
        //                 `${error.response.data.errorCode}`,
        //                 `${error.response.data.errorMessage}`)
        //         )
        //     setLoading(false);
        //     setCreateModalVisibility(false);
        // }, 15000);
    };

    const handleCancel = () => {
       setCreateModalVisibility(false);
    };

    return (
        <div>
            <CommonPageHeader
                title={"상품 목록 확인"}
                primaryButtonName={"조회"}
                primaryOnClick={onChange}
            />
        <Space direction={"horizontal"} align={"end"} style={{marginLeft : '90%'}}>
            <Button key="product_regist" onClick={showCreateModal} >신규 등록</Button>,
        </Space>
            <Table
                key = "product_list"
                className = "product_list"
                rowKey={(product): number => product.id}
                columns = {columns}
                tableLayout="auto"
                bordered={true}
                dataSource={data}
                onChange={()=>onChange()}
                onExpand={onExpand}
            />

            <Modal
                visible={createModalVisibility}
                title="신규 상품 등록"
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
                <Input
                    addonBefore="상품 이름"
                    placeholder="상품 이름을 입력하세요"
                    prefix={<InboxOutlined/>}
                    onChange={(e:React.ChangeEvent<HTMLInputElement>)=>{
                        console.log("상품이름 입력 : " + e.currentTarget.value);
                        setNewProductName(e.currentTarget.value)
                    }}/>
                <Input
                    addonBefore="상품 가격"
                    placeholder="상품 가격을 입력하세요"
                    prefix={<DollarCircleOutlined/>}
                    onChange={(e:React.ChangeEvent<HTMLInputElement>)=>{
                        console.log("상품 가격 입력 : " + e.currentTarget.value);
                        setNewProductPrice(Number(e.currentTarget.value))
                    }}/>
                </Space>
            </Modal>
        </div>
    )
}

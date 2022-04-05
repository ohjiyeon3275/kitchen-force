import { Table, Tag, Space, Modal, Button, Input } from 'antd';
import { ColumnProps } from "antd/es/table";
import { useEffect, useState } from "react";
import { DeliveryRequest, DeliveryResponse } from "../../apis/interfaces";
import { getDeliveryListApi } from "../../apis/api";
import CommonPageHeader from "../../layouts/CommonPageHeader";
import { showNotification } from "../../utils/utils";


export const DeliveryList = () => {
    const [data, setData] = useState<DeliveryResponse[]>([]);
    const [newDeliveryRequest, setNewDeliveryRequest] = useState<DeliveryRequest>();
    const [newDeliveryStatus, setDeliveryStatus] = useState("");

    const [loading, setLoading] = useState(false);

    const columns : ColumnProps<DeliveryResponse>[] = [
        {
            title: 'DeliveryId',
            dataIndex: 'id',
            key: 'id',
        },
        {
            title: 'DeliveryAddress',
            dataIndex: 'address',
            key: 'address',
        },
        {
            title: 'PhoneNumber',
            dataIndex: 'phoneNumber',
            key: 'phoneNumber',
        },
    ];

    const onChange = async() : Promise<void> => {
        try{
            const response = await getDeliveryListApi();
            setData(response);
            console.log(response);
        } catch (error) {
            // @ts-ignore
            showNotification('error', '조회 요청에 실패하였습니다.', `[${error.response.data}]`);
        }
    }

    const onExpand = async (expanded: boolean, record: DeliveryResponse) => {
        try {
            const response = await getDeliveryListApi();
            setData(response);
        } catch (error) {
            // @ts-ignore
            showNotification('error', '조회 요청에 실패하였습니다.', `[${error.response.data}]`);
        }
    }

    useEffect(() => {
        onChange()
    },[data])


    return (
        <>
        <CommonPageHeader
            title={"배달 목록 확인"}
            primaryButtonName={"조회"}
            primaryOnClick={onChange}
        />
        <Space direction={"horizontal"} align={"end"} style={{marginLeft : '90%'}}>
            <Button key="product_regist">신규 등록</Button>,
        </Space>
        <h2>djdjdjdj</h2>
        <h1>?들어왓나??</h1>
        
        </>
    )
}

import {Card, Space} from "antd";
import {useState} from "react";
import CommonPageHeader from "../../layouts/CommonPageHeader";
import {showNotification} from "../../utils/utils";

type OrderTable = {
    tableId: number,
    tableName: string,
    numberOfGuests: number,
    emptiness: boolean
}

const sampleTableOrderData : OrderTable[] = [
    {
        tableId : 1,
        tableName: '테이블 1',
        numberOfGuests: 0,
        emptiness: true
    },
    {
        tableId : 2,
        tableName: '테이블 2',
        numberOfGuests: 1,
        emptiness: false
    },
    {
        tableId : 3,
        tableName: '테이블 3',
        numberOfGuests: 2,
        emptiness: false
    },
    {
        tableId : 4,
        tableName: '테이블 4',
        numberOfGuests: 0,
        emptiness: true
    },
    {
        tableId : 5,
        tableName: '테이블 5',
        numberOfGuests: 3,
        emptiness: false
    },
    {
        tableId : 6,
        tableName: '테이블 6',
        numberOfGuests: 0,
        emptiness: true
    },
    {
        tableId : 7,
        tableName: '테이블 7',
        numberOfGuests: 2,
        emptiness: false
    },
    {
        tableId : 8,
        tableName: '테이블 8',
        numberOfGuests: 0,
        emptiness: true
    },
    {
        tableId : 9,
        tableName: '테이블 9',
        numberOfGuests: 0,
        emptiness: true
    },
    {
        tableId : 10,
        tableName: '테이블 10',
        numberOfGuests: 0,
        emptiness: true
    },
    {
        tableId : 11,
        tableName: '테이블 11',
        numberOfGuests: 0,
        emptiness: true
    }
]

const OrderTable = () => {

    const [orderTableList, setOrderTableList] = useState<OrderTable[]>(sampleTableOrderData);
    const [showModificationModal, setShowModificationModal] = useState(false);

    const onChangePrimaryButtonClick = async() => {
        showNotification(
            'warning',
            '기능 준비중 입니다.',
            '기능 준비중 '
        );
    }

    return (
        <div>
            <CommonPageHeader
                title={"테이블 주문 현황"}
                primaryButtonName={"조회"}
                primaryOnClick={onChangePrimaryButtonClick}
            />
            <Space size = {[5,2]} wrap>
                {
                    orderTableList.map((table)=> {
                        return (
                            <Card
                                key = {table.tableId}
                                title={table.tableName}
                                style={{width: 300}}
                                headStyle={(table.emptiness)?{backgroundColor:'#00FF80'}:{backgroundColor:'#FF3333'}}>
                            <p>손님 수 : {table.numberOfGuests}</p>
                            <p style={(table.emptiness)?{color:'#00FF80'}:{color:'#FF3333'}}>공석 여부 : {(table.emptiness) ? '예약가능' : '예약불가'}</p>
                        </Card>);
                    })
                }
            </Space>
        </div>
    );
}

export default OrderTable;

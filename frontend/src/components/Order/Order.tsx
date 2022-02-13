import {PageHeader} from "antd";
import CommonPageHeader from "../../layouts/CommonPageHeader";
import {showNotification} from "../../utils/utils";

export const Order = () => {

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
                title={"주문 현황 관리"}
                primaryButtonName={"조회"}
                primaryOnClick={onChangePrimaryButtonClick}
            />
            <PageHeader
                className="site-page-header"
                onBack={() => null}
                title="준비중입니다."
                subTitle="주문 화면 입니다."
            />
            <img src={"http://image.auction.co.kr/itemimage/b1/01/57/b10157b36.jpg"}/>
        </div>
    )
}

import {PageHeader} from "antd";

export const Delivery = () => {
    return (
        <div>
            <PageHeader
                className="site-page-header"
                onBack={() => null}
                title="준비중입니다."
                subTitle="Delivery 화면 입니다."
            />
            <img src={"http://image.auction.co.kr/itemimage/b1/01/57/b10157b36.jpg"}/>
        </div>
    )
}

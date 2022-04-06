import { PageHeader } from "antd";
import { Link } from 'react-router-dom';


export const Delivery = () => {
    return (
        <div>
            <PageHeader
                className="site-page-header"
                onBack={() => null}
                title="준비중입니다."
                subTitle="Delivery 화면 입니다."
            />
            <Link
                to="../delivery/list">작업중인 프론트 화면</Link>

            <img src={"http://image.auction.co.kr/itemimage/b1/01/57/b10157b36.jpg"}/>
        </div>
    )
}

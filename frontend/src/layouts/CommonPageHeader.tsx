import {Button, PageHeader} from "antd";
import {Descriptions} from "antd/es";

type CommonHeaderProps = {
    title: string,
    subTitle?: string,
    primaryButtonName?: string,
    primaryOnClick?: ()=>{},
}
const CommonPageHeader = ({title, subTitle, primaryButtonName, primaryOnClick}:CommonHeaderProps) => {
    return (
        <div>
            <PageHeader
                ghost={false}
                onBack={() => window.history.back()}
                title={title}
                subTitle={subTitle}
                extra={[
                    <Button key="3">Operation</Button>,
                    <Button key="2">Operation</Button>,
                    <Button key="1" type="primary" onClick={primaryOnClick}>
                        {primaryButtonName}
                    </Button>,
                ]}
            >
                {/*<Descriptions size="small" column={3}>*/}
                {/*    <Descriptions.Item label="Created">Lili Qu</Descriptions.Item>*/}
                {/*    <Descriptions.Item label="Association">*/}
                {/*        <a>421421</a>*/}
                {/*    </Descriptions.Item>*/}
                {/*    <Descriptions.Item label="Creation Time">2017-01-10</Descriptions.Item>*/}
                {/*    <Descriptions.Item label="Effective Time">2017-10-10</Descriptions.Item>*/}
                {/*    <Descriptions.Item label="Remarks">*/}
                {/*        Gonghu Road, Xihu District, Hangzhou, Zhejiang, China*/}
                {/*    </Descriptions.Item>*/}
                {/*</Descriptions>*/}
            </PageHeader>
        </div>
    );
}

export default CommonPageHeader;

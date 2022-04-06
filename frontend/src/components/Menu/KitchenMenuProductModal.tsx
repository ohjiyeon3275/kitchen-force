import {ProductResponse} from "../../apis/interfaces";

type KitchenMenuProductModalProps = {
    title: string,
    productList : ProductResponse[],
    handleOk? : () =>{},
    handleCancel? : () =>{}
}

const KitchenMenuProductModal
    = ({title, productList, handleOk, handleCancel}:KitchenMenuProductModalProps) => {

}

export default KitchenMenuProductModal;
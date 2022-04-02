import axios, { AxiosError } from "axios";
import { ProductRequest, ProductResponse, DeliveryResponse } from "./interfaces";
import { showNotification } from "../utils/utils";

async function getProductListApi() : Promise<ProductResponse[]> {
    const response = await axios.get("/api/products")
    console.log("Axios-product-list : " + response);
    return response.data;
}

async function createProduct(productName : string|null, price: number) : Promise<ProductResponse> {
    console.log("Axios-product-create - productRequest : " + productName);
    const response = await axios.post<ProductResponse>("/api/products", {name:productName, price:price});
    console.log("Axios-product-create : " + response);
    return response.data;
}

async function getDeliveryListApi() : Promise<DeliveryResponse[]> {
    const response = await axios.get("http://localhost:8080/api/delivery/list")
    console.log("delivery-list get : " + response);
    return response.data;
}



export {getProductListApi,createProduct, getDeliveryListApi}

export interface ProductResponse {
    id : number;
    name : string;
    price : number;
}

export interface ProductRequest {
    id? : number | null;
    name? : string;
    price? : number;
}

export interface DeliveryResponse {
    id? : number | null;
    address: String;
    phoneNumber: String;
    deliveryStatus: String,
    note: String
}

export interface DeliveryRequest {
    id? : number | null;
    deliveryStatus: String,
}
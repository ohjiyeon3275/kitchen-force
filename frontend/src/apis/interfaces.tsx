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

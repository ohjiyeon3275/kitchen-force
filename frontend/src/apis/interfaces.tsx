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

export interface MenuGroupResponse {
    id : number;
    name : string;
}

export interface MenuResponse {
    id? : number | null;
    name : string;
    price : number;
    isHidden : boolean;
    menuGroup: MenuGroupResponse;
}

export interface MenuTableData {
    key : React.Key;
    id : number;
    name : string;
    price : number;
    isHidden : boolean;
    menuGroup?: MenuGroupResponse[]|null;
}

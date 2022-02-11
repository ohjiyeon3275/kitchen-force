import React from 'react';
import {Route, Routes} from 'react-router-dom';
import {ProductList} from "../components/Product/ProductList";

const MainRouter = () => (
    <div>
        <Routes>
            <Route path="product" element = {<ProductList/>}/>
        </Routes>
    </div>
)

export default MainRouter;

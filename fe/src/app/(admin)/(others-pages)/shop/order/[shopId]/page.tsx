import PageBreadcrumb from "@/components/common/PageBreadCrumb";
import React from "react";
import Order from "@/components/order/Order";

export default function  OrderPage(){


  return (
      <div>
        <PageBreadcrumb pageTitle="Đơn hàng" />
        <Order/>
      </div>
  )
}
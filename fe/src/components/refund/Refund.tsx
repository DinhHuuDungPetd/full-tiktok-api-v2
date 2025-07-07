'use client'
import {useParams} from "next/navigation";
import React, {useEffect, useState} from "react";
import {getOrder} from "@/api/orderApi";
import type { Order } from "@/api/Type";
import {Table, TableBody, TableCell, TableHeader, TableRow} from "@/components/ui/table";
import Badge, {BadgeColor} from "@/components/ui/badge/Badge";
import ActionMutiShopDropDown from "@/components/shop/ActionMutiShopDropDown";
import {formatFullAddressFromOrder, formatOrderTime} from "@/heppler/formatOrder";
import parse from "html-react-parser";
import {getRefund} from "@/api/refundApi";

export function getOrderStatusColor(status: string): BadgeColor {
  switch (status) {
    case "UNPAID":
      return "warning"; // Chưa thanh toán
    case "ON_HOLD":
      return "info"; // Đang giữ
    case "AWAITING_SHIPMENT":
      return "primary"; // Chờ giao
    case "PARTIALLY_SHIPPING":
      return "primary"; // Giao một phần
    case "AWAITING_COLLECTION":
      return "info"; // Chờ lấy hàng
    case "IN_TRANSIT":
      return "info"; // Đang giao
    case "DELIVERED":
      return "success"; // Đã giao
    case "COMPLETED":
      return "success"; // Hoàn tất
    case "CANCELLED":
      return "error"; // Hủy
    default:
      return "dark"; // Trạng thái không rõ
  }
}
export function formatPaymentAmount(payment: {
  currency: string;
  total_amount: string;
}): string {
  const { currency, total_amount } = payment;

  const amountNumber = parseFloat(total_amount);
  if (isNaN(amountNumber)) return "0";

  return new Intl.NumberFormat("en-US", {
    style: "currency",
    currency,
    minimumFractionDigits: 0,
    maximumFractionDigits: 2,
  }).format(amountNumber);
}



export default function Refund(){

  const params = useParams();
  const shopId = params.shopId as string;

  const [refunds , setRefunds] = useState<Order[]>([]);
  const [tokenNextPage, setTokenNextPage ] = useState<string>("");
  const [isLoadingMore, setIsLoadingMore] = useState(false);

  useEffect(() => {
    (async () =>{
       await getRefund(shopId, "");
      // setRefunds(orderResponse.orders);
      // setTokenNextPage(orderResponse.nextToken);
    })();
  }, [shopId]);

  // const loadMoreOrders = async () => {
  //   if (!tokenNextPage) return;
  //   setIsLoadingMore(true);
  //   try {
  //     const orderResponse = await getOrder(shopId, tokenNextPage);
  //     const newOrders = Array.isArray(orderResponse.orders) ? orderResponse.orders : [];
  //     setOrders((prev) => [...prev, ...newOrders]);
  //     setTokenNextPage(orderResponse.nextToken);
  //   } finally {
  //     setIsLoadingMore(false);
  //   }
  // };

  return(<div>Game</div>)
}
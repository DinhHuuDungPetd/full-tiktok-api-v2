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



export default function Order (){

  const params = useParams();
  const shopId = params.shopId as string;

  const [orders , setOrders] = useState<Order[]>([]);
  const [tokenNextPage, setTokenNextPage ] = useState<string>("");
  const [isLoadingMore, setIsLoadingMore] = useState(false);

  useEffect(() => {
    (async () =>{
      const orderResponse = await getOrder(shopId, "");
      setOrders(orderResponse.orders);
      setTokenNextPage(orderResponse.nextToken);
    })();
  }, [shopId]);

  const loadMoreOrders = async () => {
    if (!tokenNextPage) return;
    setIsLoadingMore(true);
    try {
      const orderResponse = await getOrder(shopId, tokenNextPage);
      const newOrders = Array.isArray(orderResponse.orders) ? orderResponse.orders : [];
      setOrders((prev) => [...prev, ...newOrders]);
      setTokenNextPage(orderResponse.nextToken);
    } finally {
      setIsLoadingMore(false);
    }
  };



  return <div className="mt-8">
    <div className="overflow-visible rounded-xl border border-gray-200 bg-white dark:border-white/[0.05] dark:bg-white/[0.03] min-h-[70vh]">
      <div className="max-w-full overflow-visible">
        <div className="min-w-[1102px]">
          <Table>
            {/* Table Header */}
            <TableHeader className="border-b border-gray-100 dark:border-white/[0.05]">
              <TableRow>
                <TableCell
                    isHeader
                    className="px-5 py-3 font-medium text-gray-500 text-start text-theme-xs dark:text-gray-400 w-[40px]"
                >
                  STT
                </TableCell>
                <TableCell
                    isHeader
                    className="px-5 py-3 font-medium text-gray-500 text-start text-theme-xs dark:text-gray-400 w-[300px]"
                >
                  ID/Tracking/Ngày đặt
                </TableCell>
                <TableCell
                    isHeader
                    className="px-5 py-3 font-medium text-gray-500 text-start text-theme-xs dark:text-gray-400"
                >
                  Sản phẩm
                </TableCell>
                <TableCell
                    isHeader
                    className="px-5 py-3 font-medium text-gray-500 text-start text-theme-xs dark:text-gray-400"
                >
                  Trạng thái
                </TableCell>
                <TableCell
                    isHeader
                    className="px-5 py-3 font-medium text-gray-500 text-start text-theme-xs dark:text-gray-400"
                >
                  Tổng tiền
                </TableCell>
                <TableCell
                    isHeader
                    className="px-5 py-3 font-medium text-gray-500 text-start text-theme-xs dark:text-gray-400"
                >
                  Địa chỉ
                </TableCell>
                <TableCell
                    isHeader
                    className="px-5 py-3 font-medium text-gray-500 text-start text-theme-xs dark:text-gray-400"
                >
                  Xem
                </TableCell>
              </TableRow>
            </TableHeader>

            {/* Table Body */}
            <TableBody className="divide-y divide-gray-100 dark:divide-white/[0.05]">
              {orders.map((order, index) => (
                  <TableRow key ={order.id}>
                    <TableCell className="px-4 py-3 text-gray-500 text-start text-theme-sm dark:text-gray-400">
                      {`${index + 1}`}
                    </TableCell>
                    <TableCell className="px-5 py-4 sm:px-6 text-start">
                      <div className="flex items-center gap-3">
                        <div>
                              <span
                                  className="block font-medium text-gray-800 text-theme-sm dark:text-white/90">
                                {`ID: ${order.id}`}
                              </span>
                          <span
                              className="block text-gray-500 text-theme-xs dark:text-gray-400">
                                   {`Tracking: ${!order.tracking_number ? "chưa có" : order.tracking_number}`}
                            </span>
                          <span
                              className="block text-gray-500 text-theme-xs dark:text-gray-400">
                                   {formatOrderTime(order.create_time)}
                            </span>
                        </div>
                      </div>
                    </TableCell>
                    <TableCell className="px-4 py-3 text-gray-500 text-start text-theme-sm dark:text-gray-400 ">
                      <div className="flex flex-wrap gap-3 max-w-[240px] hover:bg-gray-100">
                        {order.line_items.map((line_item) => (
                            <div key={line_item.sku_id}>
                              <img
                                  className="w-[40px] h-[40px] object-cover border-1 border-gray-400 rounded-sm"
                                  src={line_item.sku_image}
                                  alt={line_item.product_name}
                              />
                            </div>
                        ))}
                      </div>
                    </TableCell>
                    <TableCell className="px-4 py-3 text-gray-500 text-start text-theme-sm dark:text-gray-400">
                      <Badge
                          size={"sm"}
                          color={getOrderStatusColor(order.status)}
                      >
                        {order.status}
                      </Badge>
                      <span
                          className="block text-gray-500 text-theme-xs dark:text-gray-400">
                                   {`Ship by: ${order.shipping_type}`}
                            </span>
                    </TableCell>
                    <TableCell className="px-4 py-3 text-gray-500 text-start text-theme-sm dark:text-gray-400">
                      {formatPaymentAmount(order.payment)}
                    </TableCell>
                    <TableCell className="px-4 py-3 text-gray-500 text-start text-theme-sm dark:text-gray-400">
                      {parse(formatFullAddressFromOrder(order.recipient_address))}
                    </TableCell>
                    <TableCell className="px-4 py-3 text-gray-500 text-start text-theme-sm dark:text-gray-400">
                      <ActionMutiShopDropDown shopId={order.status} />
                    </TableCell>
                  </TableRow>
              ))}
            </TableBody>
          </Table>
          {tokenNextPage && (
              <div className="text-center py-6">
                <button
                    onClick={loadMoreOrders}
                    disabled={isLoadingMore}
                    className="px-4 py-2 bg-blue-600 text-white rounded hover:bg-blue-700 disabled:opacity-50 disabled:cursor-not-allowed"
                >
                  {isLoadingMore ? "Đang tải..." : "Tải thêm"}
                </button>
              </div>
          )}
        </div>
      </div>
    </div>

  </div>
}
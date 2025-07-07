"use client"
import {Table, TableBody, TableCell, TableHeader, TableRow} from "@/components/ui/table";
import Badge from "@/components/ui/badge/Badge";
import React, {useEffect, useState} from "react";
import {ShopResponse} from "@/api/Type";
import {getMyShoppingList} from "@/api/shopApi";
import ActionMutiShopDropDown from "@/components/shop/ActionMutiShopDropDown";

const sampleShops: ShopResponse[] = [
  {
    shopId: "7000714532876273420",
    shopName: "Maomao Beauty UK",
    note: "Main store for cosmetics",
    country: "GB",
  },
  {
    shopId: "7000714532876273421",
    shopName: "Tech World US",
    note: "Top-selling electronics",
    country: "US",
  },
  {
    shopId: "7000714532876273422",
    shopName: "Pet Paradise",
    note: "Pet accessories and food",
    country: "GB",
  },
  {
    shopId: "7000714532876273423",
    shopName: "Green Living",
    note: "Eco-friendly home goods",
    country: "AU",
  },
  {
    shopId: "7000714532876273424",
    shopName: "Style Hub",
    note: "Trendy fashion for youth",
    country: "SG",
  },
];

export default function Shop(){

  const [shops, setShops] = useState<ShopResponse[]>([]);

  useEffect(() => {
    (async () =>{
      const shopResponse  = await getMyShoppingList();
      setShops(shopResponse);
    })();
  },[])

  return(
      <div className="mt-8">
        <div className="overflow-visible rounded-xl border border-gray-200 bg-white dark:border-white/[0.05] dark:bg-white/[0.03] min-h-[70vh]">
          <div className="max-w-full overflow-visible">
            <div className="min-w-[1102px]">
              <Table>
                {/* Table Header */}
                <TableHeader className="border-b border-gray-100 dark:border-white/[0.05]">
                  <TableRow>
                    <TableCell
                        isHeader
                        className="px-5 py-3 font-medium text-gray-500 text-start text-theme-xs dark:text-gray-400"
                    >
                      Tên shop
                    </TableCell>
                    <TableCell
                        isHeader
                        className="px-5 py-3 font-medium text-gray-500 text-start text-theme-xs dark:text-gray-400"
                    >
                      Ghi chú
                    </TableCell>
                    <TableCell
                        isHeader
                        className="px-5 py-3 font-medium text-gray-500 text-start text-theme-xs dark:text-gray-400"
                    >
                      Khu vực
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
                  {shops.map((shop, index) => (
                      <TableRow key ={shop.shopId}>
                        <TableCell className="px-5 py-4 sm:px-6 text-start">
                          <div className="flex items-center gap-3">
                            <div>
                              <span
                                  className="block font-medium text-gray-800 text-theme-sm dark:text-white/90">
                                {`${index +1 }. ${shop.shopName}`}
                              </span>
                              <span
                                  className="block text-gray-500 text-theme-xs dark:text-gray-400">
                                   {shop.shopId}
                            </span>
                            </div>
                          </div>
                        </TableCell>
                        <TableCell className="px-4 py-3 text-gray-500 text-start text-theme-sm dark:text-gray-400">
                          {shop.note}
                        </TableCell>
                        <TableCell className="px-4 py-3 text-gray-500 text-start text-theme-sm dark:text-gray-400">
                          <Badge
                              size={"sm"}
                          >
                            {shop.country}
                          </Badge>
                        </TableCell>
                        <TableCell className="px-4 py-3 text-gray-500 text-start text-theme-sm dark:text-gray-400">
                          <ActionMutiShopDropDown shopId={shop.shopId} />
                        </TableCell>
                      </TableRow>
                  ))}
                </TableBody>
              </Table>
            </div>
          </div>
        </div>

      </div>
  )

}
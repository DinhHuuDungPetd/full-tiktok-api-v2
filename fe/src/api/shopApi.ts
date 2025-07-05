import AxiosInstance from "@/until/AxiosInstance";
import {ApiResponse, ShopRequest, ShopResponse} from "@/api/Type";
import axios from "axios";

export async function getMyShoppingList() {
  try {
    const response =await  AxiosInstance.get<ApiResponse<ShopResponse[]>>("/shop/my-shops");
    return response.data.result;
  }catch (error ) {
    if (axios.isAxiosError(error) && error.response) {
      const message = error.response.data?.message || "Lỗi không xác định";
      throw new Error(message);
    }

    throw new Error("Lỗi không xác định");
  }
}

export async function addShop(request: ShopRequest) {
  try {
    const response =
        await AxiosInstance.post<ApiResponse<ShopResponse>>("add/shop", request);
    return response.data;
  }catch (error ) {
    console.error(error);
    // @ts-expect-error - Error object may have data property
    if (error?.data ) {
      console.log("sdjadhjadjakjdasd")
      // @ts-expect-error - Error object may have data property
      const message = error.data.message || "Lỗi không xác định";
      throw new Error(message);
    }
    throw new Error("Lỗi không xác định");
  }
}
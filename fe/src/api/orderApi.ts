import AxiosInstance from "@/until/AxiosInstance";
import axios from "axios";
import {Order, OrderApiResponse} from "@/api/Type";

type ResponseOrder = {
  orders: Order[];
  nextToken: string;
  totalCount: number;

}

export async function getOrder(shopId: string, nextPage: string): Promise<ResponseOrder> {
  try {
    const { data }: OrderApiResponse = await AxiosInstance.get(`orders/${shopId}/shop?next_page=${nextPage}`);
    console.log(data);
    return {
      orders: data.orders,
      nextToken: data.next_page_token,
      totalCount: data.total_count
    };
  } catch (error) {
    if (axios.isAxiosError(error) && error.response) {
      const message = error.response.data?.message || "Lỗi không xác định";
      throw new Error(message);
    }
    throw new Error("Lỗi không xác định");
  }
}
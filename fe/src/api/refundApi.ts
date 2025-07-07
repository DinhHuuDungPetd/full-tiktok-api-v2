import AxiosInstance from "@/until/AxiosInstance";
import axios from "axios";


export async function getRefund(shopId: string, nextPage: string): Promise<any> {
  try {
    const response = await AxiosInstance.get(`refund/${shopId}/shop?next_page=${nextPage}`);
    console.log(response);
  } catch (error) {
    if (axios.isAxiosError(error) && error.response) {
      const message = error.response.data?.message || "Lỗi không xác định";
      throw new Error(message);
    }
    throw new Error("Lỗi không xác định");
  }
}
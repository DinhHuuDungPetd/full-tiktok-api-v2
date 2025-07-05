"use client";
import Checkbox from "@/components/form/input/Checkbox";
import Input from "@/components/form/input/InputField";
import Label from "@/components/form/Label";
import Button from "@/components/ui/button/Button";
import { ChevronLeftIcon, EyeCloseIcon, EyeIcon } from "@/icons";
import Link from "next/link";
import React, { useState } from "react";
import {useRouter, useSearchParams} from "next/navigation";
import {validateEmail, validatePassword} from "@/heppler/validation";
import {toast} from "react-toastify";
import {LoginRequest} from "@/api/Type";
import login from "@/api/loginApi";

export default function SignInForm() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [showPassword, setShowPassword] = useState(false);

  const searchParams = useSearchParams();
  const callBackUrl = searchParams.get("call_back_url");
  const router = useRouter();

  const validateForm = (): boolean => {
    if (!validateEmail(email)) {
      toast.error("Email chưa hợp lệ!");
      return false;
    }
    if (!validatePassword(password)) {
      toast.error("Mật khẩu chưa hợp lệ!");
      return false;
    }
    return true;
  };

  const handleSuccessLogin = () => {
    const redirectTo = callBackUrl || "/";
    router.push(redirectTo);
  };

  const handleLogin = async () => {
    if (!validateForm()) return;
    const request: LoginRequest = { email, password };
    try {
      const res = await login(request);
      console.log(res);
      handleSuccessLogin();
    } catch (err: any) {
      const status = err?.data?.status || 5000;
      if (status === 4004) {
        toast.error("Tài khoản không đúng!");
      } else {
        toast.error(err?.message || "Lỗi chưa xác định");
      }
    }
  };
  return (
    <div className="flex flex-col flex-1 lg:w-1/2 w-full">
      <div className="flex flex-col justify-center flex-1 w-full max-w-md mx-auto">
        <div>
          <div className="mb-5 sm:mb-8">
            <h1 className="mb-2 font-semibold text-gray-800 text-title-sm dark:text-white/90 sm:text-title-md">
              Đăng nhập
            </h1>
            <p className="text-sm text-gray-500 dark:text-gray-400">
              Nhập email và mật khẩu admin cấp để đăng nhập!
            </p>
          </div>
          <div>
            <div>
              <div className="space-y-6">
                <div>
                  <Label>
                    Email <span className="text-error-500">*</span>{" "}
                  </Label>
                  <Input
                      defaultValue={email}
                      onChange={(e) => setEmail(e.target.value)}
                      placeholder="info@gmail.com"
                      type="email"
                  />
                </div>
                <div>
                  <Label>
                    Mật khẩu <span className="text-error-500">*</span>{" "}
                  </Label>
                  <div className="relative">
                    <Input
                        defaultValue={password}
                        onChange={(e) => setPassword(e.target.value)}
                        type={showPassword ? "text" : "password"}
                        placeholder="Enter your password"
                    />
                    <span
                      onClick={() => setShowPassword(!showPassword)}
                      className="absolute z-30 -translate-y-1/2 cursor-pointer right-4 top-1/2"
                    >
                      {showPassword ? (
                        <EyeIcon className="fill-gray-500 dark:fill-gray-400" />
                      ) : (
                        <EyeCloseIcon className="fill-gray-500 dark:fill-gray-400" />
                      )}
                    </span>
                  </div>
                </div>
                <div className="flex items-center justify-between">
                  <Link
                    href="/reset-password"
                    className="text-sm text-brand-500 hover:text-brand-600 dark:text-brand-400"
                  >
                    Quên mật khẩu?
                  </Link>
                </div>
                <div>
                  <Button className="w-full" size="sm" onClick={handleLogin}>
                    Đăng nhập
                  </Button>
                </div>
              </div>
            </div>

            <div className="mt-5">
              <p className="text-sm font-normal text-center text-gray-700 dark:text-gray-400 sm:text-start">
                Bạn chưa có tài  khoản? Liên hệ zalo 0386117963 {""}
              </p>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

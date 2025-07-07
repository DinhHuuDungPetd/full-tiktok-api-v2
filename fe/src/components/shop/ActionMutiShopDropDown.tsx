"use client";
import React, { useState } from "react";
import { Dropdown } from "../ui/dropdown/Dropdown";
import { DropdownItem } from "../ui/dropdown/DropdownItem";
import {
  ClipboardList,
  BanknoteArrowDown, CircleDollarSign
} from "lucide-react";

type props = {
  shopId: string;
}

export default function ActionMutiShopDropDown({ shopId }: props) {
  const [isOpen, setIsOpen] = useState(false);

  function toggleDropdown(e: React.MouseEvent<HTMLButtonElement, MouseEvent>) {
    e.stopPropagation();
    setIsOpen((prev) => !prev);
  }

  function closeDropdown() {
    setIsOpen(false);
  }
  return (
      <div className="relative max-w-[100px]">
        <button
            onClick={toggleDropdown}
            className="flex items-center text-gray-700 dark:text-gray-400 dropdown-toggle"
        >
          <span className="block mr-1 font-medium text-theme-sm">Chọn</span>

          <svg
              className={`stroke-gray-500 dark:stroke-gray-400 transition-transform duration-200 ${
                  isOpen ? "rotate-180" : ""
              }`}
              width="18"
              height="20"
              viewBox="0 0 18 20"
              fill="none"
              xmlns="http://www.w3.org/2000/svg"
          >
            <path
                d="M4.3125 8.65625L9 13.3437L13.6875 8.65625"
                stroke="currentColor"
                strokeWidth="1.5"
                strokeLinecap="round"
                strokeLinejoin="round"
            />
          </svg>
        </button>

        <Dropdown
            isOpen={isOpen}
            onClose={closeDropdown}
            className="absolute right-0  flex min-w-[200px] flex-col rounded-2xl border border-gray-200 bg-white shadow-theme-lg dark:border-gray-800 dark:bg-gray-dark z-10"
        >
          <ul className="flex flex-col gap-1 pt-2 pb-2 border-b border-gray-200 dark:border-gray-800">
            <li>
              <DropdownItem
                  onItemClick={closeDropdown}
                  tag="a"
                  href={`/shop/order/${shopId}`}
                  className="flex items-center gap-3 font-medium text-gray-700 rounded-lg group text-theme-xs hover:bg-gray-100 hover:text-gray-700 dark:text-gray-400 dark:hover:bg-white/5 dark:hover:text-gray-300"
              >
                <ClipboardList size ={18} />
                Đơn hàng
              </DropdownItem>
            </li>
            <li>
              <DropdownItem
                  onItemClick={closeDropdown}
                  tag="a"
                  href={`/shop/refund/${shopId}`}
                  className="flex items-center gap-3 font-medium text-gray-700 rounded-lg group text-theme-xs hover:bg-gray-100 hover:text-gray-700 dark:text-gray-400 dark:hover:bg-white/5 dark:hover:text-gray-300"
              >
                <BanknoteArrowDown size={18} />
                Đổi/trả hàng
              </DropdownItem>
            </li>
            <li>
              <DropdownItem
                  onItemClick={closeDropdown}
                  tag="a"
                  href="/profile"
                  className="flex items-center gap-3 font-medium text-gray-700 rounded-lg group text-theme-xs hover:bg-gray-100 hover:text-gray-700 dark:text-gray-400 dark:hover:bg-white/5 dark:hover:text-gray-300"
              >
                <CircleDollarSign  size={18}/>
                Tiền về
              </DropdownItem>
            </li>
          </ul>
        </Dropdown>
      </div>
  );
}

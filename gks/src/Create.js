import React, { useState } from "react"; // React와 useState 훅을 임포트
import { useNavigate } from "react-router-dom"; // useNavigate 훅을 임포트
import axios from "axios"; // axios를 임포트

const Create = () => {
  const [username, setUsername] = useState(""); // 사용자 이름 상태
  const [password, setPassword] = useState(""); // 비밀번호 상태
  const [confirmPassword, setConfirmPassword] = useState(""); // 비밀번호 확인 상태
  const [employeeId, setEmployeeId] = useState(""); // 직원 ID 상태
  const navigate = useNavigate(); // 페이지 이동을 위한 navigate 함수

  const handleSubmit = async () => {
    // 폼 제출 핸들러
    if (password !== confirmPassword) {
      // 비밀번호가 일치하지 않는 경우
      alert("비밀번호가 일치하지 않습니다."); // 경고 메시지 표시
      return;
    }
    try {
      const response = await axios.post("/api/create", {
        // 서버에 POST 요청
        username,
        password,
        employeeId,
      });
      if (response.data.createSuccess) {
        // 성공 메시지가 포함된 경우
        alert(response.data.createSuccess); // 성공 메시지 표시
        navigate("/"); // 홈으로 이동
      } else if (response.data.createError) {
        // 오류 메시지가 포함된 경우
        alert(response.data.createError); // 오류 메시지 표시
      }
    } catch (error) {
      console.error("Create error:", error); // 오류 발생 시 콘솔에 오류 로그
    }
  };

  return (
    <div>
      <h2>Sign Up</h2> {/* 제목 표시 */}
      <input
        type="text"
        placeholder="Username"
        onChange={(e) => setUsername(e.target.value)} // 사용자 이름 상태 업데이트
      />
      <input
        type="password"
        placeholder="Password"
        onChange={(e) => setPassword(e.target.value)} // 비밀번호 상태 업데이트
      />
      <input
        type="password"
        placeholder="Confirm Password"
        onChange={(e) => setConfirmPassword(e.target.value)} // 비밀번호 확인 상태 업데이트
      />
      <input
        type="text"
        placeholder="Employee ID"
        onChange={(e) => setEmployeeId(e.target.value)} // 직원 ID 상태 업데이트
      />
      <button onClick={handleSubmit}>Submit</button>{" "}
      {/* 제출 버튼 클릭 시 handleSubmit 호출 */}
      <button onClick={() => navigate("/")}>Home</button>{" "}
      {/* 홈 버튼 클릭 시 홈으로 이동 */}
    </div>
  );
};

export default Create; // Create 컴포넌트를 기본 내보내기로 설정

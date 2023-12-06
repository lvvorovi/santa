import logo from "./logo.svg";
import "./App.css";
import { HashRouter, Route, Routes } from "react-router-dom";
import { GroupList } from "./components/GroupList";
import { ViewGroup } from "./components/ViewGroup";
import { CreateUser } from "./components/CreateUser";
import { CreateGift } from "./components/CreateGift";
import { CreateGroup } from "./components/CreateGroup";
import { ViewUser } from "./components/ViewUser";
import { EditGift } from "./components/EditGift";
import ChatRoom from "./components/ChatRoom";

// const apiUrl = "http://localhost:8085";

function App() {
  return (
    <div className="App">
      <h1>HO-HO-HO</h1>
      <HashRouter>
        <div className="container">
          <Routes>
            <Route path="/" element={<GroupList />} />
            <Route path="/users" element={<CreateUser />} />
            <Route path="/create/gift/:id" element={<CreateGift />} />
            <Route path="/users/:userId/groups/:groupId" element={<ViewGroup />} />
            <Route path="/create/group/:id" element={<CreateGroup />} />
            <Route path="/users/:id" element={<ViewUser />} />
            <Route path="/users/:userId/gifts/:giftId" element={<EditGift />} />
            <Route path="/chatroom" element={<ChatRoom />} />
          </Routes>
        </div>
      </HashRouter>
    </div>
  );
}

export default App;
// export { apiUrl };

import logo from "./logo.svg";
import "./App.css";
import { HashRouter, Route, Routes } from "react-router-dom";
import { GroupList } from "./components/GroupList";
import { ViewGroup } from "./components/ViewGroup";
import { CreateUser } from "./components/CreateUser";
import { CreateGift } from "./components/CreateGift";
import { CreateGroup } from "./components/CreateGroup";

function App() {
  return (
    <div className="App">
      <h1>HO-HO-HO</h1>
      <HashRouter>
        <div className="container">
          <Routes>
            <Route path="/" element={<GroupList />} />
            <Route parh="/users" element={<CreateUser />} />
            <Route parh="/gifts" element={<CreateGift />} />
            <Route path="/groups/:id" element={<ViewGroup />} />
            <Route path="/create/group" element={<CreateGroup />} />
          </Routes>
        </div>
      </HashRouter>
    </div>
  );
}

export default App;

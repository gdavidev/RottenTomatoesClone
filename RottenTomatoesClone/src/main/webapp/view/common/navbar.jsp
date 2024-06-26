<nav class="navbar navbar-expand-md navbar-dark bg-dark px-5">
	<a class="navbar-brand" href="#">Rot Tomato</a>
	<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarsExample04" aria-controls="navbarsExample04" aria-expanded="false" aria-label="Toggle navigation">
		<span class="navbar-toggler-icon"></span>
	</button>
		
	<div class="collapse navbar-collapse justify-content-between" id="navbarsExample03">
		<ul class="navbar-nav">
			<li class="nav-item active">
				<a class="nav-link" href="<%=request.getContextPath() %>/home">Home</a>
			</li>
			<li class="nav-item">
				<a class="nav-link" href="<%=request.getContextPath() %>/movies">All movies</a>
			</li>
		</ul>
		<ul class="navbar-nav column-gap-2">
			<li class="nav-item">
				<form class="my-2 my-md-0">
					<input class="form-control" type="text" placeholder="Search">
				</form>
			</li>
			<%if (request.getSession().getAttribute("loggedUser") != null) { %>
				<li class="nav-item dropdown">
				  	<button class="btn btn-secondary dropdown-toggle" type="button" data-bs-toggle="dropdown" aria-expanded="false">
			    		<%=request.getSession().getAttribute("loggedUser") %>
				  	</button>
				  	<ul class="dropdown-menu">
				   		<li><a class="dropdown-item" href="#">Meus favoritos</a></li>
					    <li><a class="dropdown-item" href="#">Sair</a></li>				    
				  	</ul>
	         	</li>
         	<%} else { %>
         		<li><a class="btn btn-primary" href="<%=request.getContextPath() %>/user/login">Login</a></li>
         		<li><a class="btn btn-secondary" href="<%=request.getContextPath() %>/user/register">Sign-in</a></li>
         	<%} %>
		</ul>	
	</div>
</nav>